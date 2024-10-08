package me.xiaoying.livegetauthorize.server.file;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.configuration.YamlConfiguration;
import me.xiaoying.livegetauthorize.server.utils.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

public abstract class SubFile {
    YamlConfiguration configuration;

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(YamlConfiguration configuration) {
        this.configuration = configuration;
    }

    public abstract void newFile();

    public abstract void delFile();

    public abstract void initFile();

    public void saveResource(String resourcePath, boolean replace) {
        if (resourcePath == null || resourcePath.equals(""))
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = getResource(resourcePath);
        if (in == null)
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found");

        File outFile = new File(resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(resourcePath.substring(0, Math.max(lastIndex, 0)));

        if (!outDir.exists())
            outDir.mkdirs();

        try {
            if (outFile.exists() && !replace)
                LACore.getLogger().warn("Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");

            OutputStream out = Files.newOutputStream(outFile.toPath());
            byte[] buf = new byte[in.available()];
            int len;
            while ((len = in.read(buf)) > 0)
                out.write(buf, 0, len);

            out.close();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取包内文件
     *
     * @param filename 文件名称
     * @return InputStream
     */
    public InputStream getResource(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        }

        try {
            URL url = SubFile.class.getClassLoader().getResource(filename);

            if (url == null) {
                return null;
            }

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException iOException) {
            return null;
        }
    }

    public String getString(String object, String path) {
        if (!StringUtil.isEmpty(this.configuration.getString(path)))
            return this.configuration.getString(path);

        return object;
    }

    public int getInt(int object, String path) {
        if (StringUtil.isEmpty(this.configuration.getString(path)))
            return object;

        try {
            return Integer.parseInt(this.configuration.getString(path));
        } catch (Exception e) {
            return object;
        }
    }

    public long getLong(long object, String path) {
        if (StringUtil.isEmpty(this.configuration.getString(path)))
            return object;

        try {
            return Long.parseLong(this.configuration.getString(path));
        } catch (Exception e) {
            return object;
        }
    }
}