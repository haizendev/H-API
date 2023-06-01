package fr.haizen.hapi.saveable;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;

public class FileBuilder {
	
	private File f;
	private YamlConfiguration c;
	private String name;
	private String path;

	public FileBuilder(String FilePath, String FileName) {
		this.f = new File(FilePath, FileName);
		this.path = FilePath;
		this.name = FileName;
		this.c = YamlConfiguration.loadConfiguration(this.f);
	}

	public FileBuilder setValue(String ValuePath, Object Value) {
		this.c.set(ValuePath, Value);
		return this;
	}

	public void setName(String name) {
		File newFile = new File(path, name + ".yml");
		this.f.renameTo(newFile);
		this.name = name;
	}
	
	public void deleteFileOrFolder(final Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(final Path file, final IOException e) {
                return handleException(e);
            }

            private FileVisitResult handleException(final IOException e) {
                e.printStackTrace(); // replace with more robust error handling
                return FileVisitResult.TERMINATE;
            }

            @Override
            public FileVisitResult postVisitDirectory(final Path dir, final IOException e) throws IOException {
                if (e != null)
                    return handleException(e);
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    };

	public String getName() {
		return this.name;
	}

	public Object getObject(String ValuePath) { return this.c.get(ValuePath); }

	public void removeFile(String FileName) {
		if (f.getAbsoluteFile().getName() != null) {
			f.getAbsoluteFile().delete();
		}else {

		}
	}

	public void removeBuilder(String FilePath) { this.f.delete(); }

	public int getInt(String ValuePath) { return this.c.getInt(ValuePath); }

	public String getString(String ValuePath) { return this.c.getString(ValuePath); }

	public long getLong(String ValuePath) { return this.c.getLong(ValuePath); }

	public boolean getBoolean(String ValuePath) { return this.c.getBoolean(ValuePath); }

	public List<String> getStringList(String ValuePath) { return this.c.getStringList(ValuePath); }

	public Set<String> getKeys(boolean dep) { return this.c.getKeys(dep); }

	public ConfigurationSection getConfigurationSection(String section) { return this.c.getConfigurationSection(section); }

	public boolean exist() { return this.f.exists(); }

	public FileBuilder save() {
		try {
			this.c.save(this.f);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return this;
	}
}
