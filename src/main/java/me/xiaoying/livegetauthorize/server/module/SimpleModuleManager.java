package me.xiaoying.livegetauthorize.server.module;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.NamespacedKey;
import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.core.module.Module;
import me.xiaoying.livegetauthorize.core.module.ModuleManager;
import me.xiaoying.livegetauthorize.core.plugin.Plugin;
import me.xiaoying.livegetauthorize.server.Application;
import me.xiaoying.livegetauthorize.server.constant.ConstantCommon;
import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;
import me.xiaoying.livegetauthorize.server.utils.DateUtil;
import me.xiaoying.livegetauthorize.server.utils.StringUtil;
import me.xiaoying.sql.SqlFactory;
import me.xiaoying.sql.entity.Column;
import me.xiaoying.sql.entity.Condition;
import me.xiaoying.sql.entity.ConditionType;
import me.xiaoying.sql.entity.Record;
import me.xiaoying.sql.sentence.*;

import java.util.*;

/**
 * Manager Module
 */
public class SimpleModuleManager implements ModuleManager {
    private final Map<String, Module> knownModule = new HashMap<>();
    private final Map<String, Module> identificationModule = new HashMap<>();

    public SimpleModuleManager() {
        LACore.getLogger().info("Initializing module manager...");

        // 初始化数据表
        SqlFactory sqlFactory = Application.getSqlFactory();
        Create create = new Create(ConstantCommon.MODULE_INFO);
        Stack<Column> columns = new Stack<>();
        columns.add(new Column("function", "varchar", 255));
        columns.add(new Column("description", "varchar", 255));
        columns.add(new Column("child", "longtext", 0));
        columns.add(new Column("identification", "varchar", 255));
        columns.add(new Column("permission", "varchar", 255));
        create.setColumns(columns);
        sqlFactory.sentence(create).run();

        // 初始化
        this.initialize();
    }

    public void initialize() {
        SqlFactory sqlFactory = Application.getSqlFactory();
        Select select = new Select(ConstantCommon.MODULE_INFO);
        Stack<Record> records = sqlFactory.sentence(select).run();
        records.forEach(record -> {
            Module module = new ServerModule(null, record.get("function").toString(), record.get("description").toString(), record.get("identification").toString(), record.get("permission").toString());
            if (record.get("child") != null && !StringUtil.isEmpty(record.get("child").toString()))
                this.handleChild(module, record.get("child").toString());

            this.registerModule(module);
        });
    }

    private void handleChild(Module module, String string) {
        String description = module.getDescription();
        String identification = module.getIdentification();
        String permission = module.getPermission();

        JSONArray jsonArray = JSONArray.of(string);

        for (Object o : jsonArray.getJSONArray(0)) {
            String name;
            User owner = null;
            Date save = null, over = null;
            JSONObject jsonObject = (JSONObject) o;
            name = jsonObject.getString("name");
            if (!StringUtil.isEmpty(jsonObject.getString("owner")))
                owner = Application.getUserManager().getUserByUUID(jsonObject.getString("owner"));
            if (!StringUtil.isEmpty(jsonObject.getString("save")) && !StringUtil.isEmpty(jsonObject.getString("over"))) {
                save = DateUtil.stringToDate(jsonObject.getString("save"), FileConfigConstant.SETTING_DATEFORMAT);
                over = DateUtil.stringToDate(jsonObject.getString("over"), FileConfigConstant.SETTING_DATEFORMAT);
            }
            ServerModule m = new ServerModule(owner, name, description, identification, permission, save, over, module);
            ((ServerModule) module).registerChild(m);
        }
    }

    public void create(Module module) {
        // module
        if (module.getParent() != null) {
            SqlFactory sqlFactory = Application.getSqlFactory();
            Insert insert = new Insert(((ServerModule) module).getTable());
            insert.insert(module.getName(), module.getDescription(), "", module.getIdentification(), module.getPermission());
            sqlFactory.sentence(insert).run();
            return;
        }

        // 子 Module
        SqlFactory sqlFactory = Application.getSqlFactory();
        ServerModule parent = (ServerModule) module.getParent();
        parent.registerChild(module);
        Update update = new Update(parent.getTable());
        update.set("child", parent.getModuleChildrenAsString());
        sqlFactory.sentence(update).condition(new Condition("function", parent.getName(), ConditionType.EQUAL)).run();
    }

    public void delete(Module module) {
        if (module.getParent() != null) {
            SqlFactory sqlFactory = Application.getSqlFactory();
            Delete delete = new Delete(((ServerModule) module).getTable());
            sqlFactory.sentence(delete).condition(new Condition("function", module.getName(), ConditionType.EQUAL)).run();
            return;
        }

        // 子 Module
        SqlFactory sqlFactory = Application.getSqlFactory();
        ServerModule parent = (ServerModule) module.getParent();
        parent.unregisterModuleChild(module.getName());
        Update update = new Update(parent.getTable());
        update.set("child", parent.getModuleChildrenAsString());
        sqlFactory.sentence(update).condition(new Condition("function", parent.getName(), ConditionType.EQUAL)).run();
    }

    private void registerModule(Module module) {
        this.knownModule.put("authorizecore:" + module.getName().toLowerCase(Locale.ROOT), module);
        this.identificationModule.put(module.getIdentification(), module);
    }

    @Override
    public void registerModule(Plugin plugin, Module module) {
        this.knownModule.put(new NamespacedKey(plugin, module.getName()).toString(), module);
        this.identificationModule.put(module.getIdentification(), module);
    }

    @Override
    public void unregisterModule(Module module) {
        Iterator<String> iterator = this.knownModule.keySet().iterator();
        String string;
        while (iterator.hasNext() && (string = iterator.next()) != null) {
            if (this.knownModule.get(string) != module)
                continue;

            iterator.remove();
        }
    }

    @Override
    public void unregisterModule(String function) {
        if (function.startsWith("authorizecore:"))
            return;

        this.knownModule.remove(function);
    }

    @Override
    public void unregisterModules() {
        this.knownModule.clear();
    }

    @Override
    public void unregisterModules(Plugin plugin) {
        Iterator<String> iterator = this.knownModule.keySet().iterator();
        String string;
        while (iterator.hasNext() && (string = iterator.next()) != null) {
            if (!string.startsWith(plugin.getDescription().getName().toLowerCase(Locale.ROOT) + ":"))
                continue;

            iterator.remove();
        }
    }

    @Override
    public Module getModule(String function) {
        function = function.toLowerCase(Locale.ROOT);
        if (this.knownModule.containsKey(function))
            return this.knownModule.get(function);
        if (function.contains(":"))
            return this.knownModule.get(function);

        for (String s : this.knownModule.keySet()) {
            if (!s.endsWith(":" + function))
                continue;

            return this.knownModule.get(s);
        }
        return null;
    }

    @Override
    public Module getModuleByIdentification(String identification) {
        return this.identificationModule.get(identification);
    }

    @Override
    public List<Module> getModules() {
        return new ArrayList<>(this.knownModule.values());
    }
}