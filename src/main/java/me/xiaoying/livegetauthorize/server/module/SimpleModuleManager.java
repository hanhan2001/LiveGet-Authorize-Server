package me.xiaoying.livegetauthorize.server.module;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.NamespacedKey;
import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.core.module.Module;
import me.xiaoying.livegetauthorize.core.module.ModuleChild;
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
import me.xiaoying.sql.sentence.Create;
import me.xiaoying.sql.sentence.Insert;
import me.xiaoying.sql.sentence.Select;
import me.xiaoying.sql.sentence.Update;

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
            Module module = new Module(record.get("function").toString(), record.get("description").toString(), record.get("identification").toString(), record.get("permission").toString());
            if (!StringUtil.isEmpty(record.get("child").toString()))
                this.handleChild(module, record.get("child").toString());
            this.registerModule(module);
        });
    }

    private void handleChild(Module module, String string) {
        for (String s : string.split(",")) {
            String[] split = s.replace(" ", "").split("~");
            String name = split[0];
            User user = Application.getUserManager().findUser("uuid", split[1]);
            if (split.length == 2) {
                module.registerChild(new ModuleChild(name, null, null, user, module));
                continue;
            }
            Date save = DateUtil.stringToDate(split[2].toString(), FileConfigConstant.SETTING_DATEFORMAT);
            Date over = DateUtil.stringToDate(split[3].toString(), FileConfigConstant.SETTING_DATEFORMAT);
            module.registerChild(new ModuleChild(name, save, over, user, module));
        }
    }

    public void create(Module module) {
        // module
        // | function | description | child | identification | permission |
        // | minecraft | Any | 764932129~user~2024/05/31-10:10:47~2024/05/31-15:10:47,730521870 | Any | Any |
        SqlFactory sqlFactory = Application.getSqlFactory();
        Insert insert = new Insert(ConstantCommon.MODULE_INFO);
        insert.insert(module.getFunction(), module.getDescription(), module.getModuleChildrenAsString(), module.getIdentification(), module.getPermission());
        sqlFactory.sentence(insert).run();
    }

    public void create(ModuleChild moduleChild) {
        Module module = moduleChild.getModule();
        module.registerChild(moduleChild);
        SqlFactory sqlFactory = Application.getSqlFactory();
        Update update = new Update(ConstantCommon.MODULE_INFO);
        update.set("child", module.getModuleChildrenAsString());
        sqlFactory.sentence(update).condition(new Condition("function", module.getFunction(), ConditionType.EQUAL)).run();
    }

    private void registerModule(Module module) {
        this.knownModule.put("authorizecore:" + module.getFunction().toLowerCase(Locale.ROOT), module);
        this.identificationModule.put(module.getIdentification(), module);
    }

    @Override
    public void registerModule(Plugin plugin, Module module) {
        this.knownModule.put(new NamespacedKey(plugin, module.getFunction()).toString(), module);
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