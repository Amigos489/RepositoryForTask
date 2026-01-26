package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JsonStorage {

    private final ObjectMapper mapper;
    private final String filePath;

    public JsonStorage(String filePath) {
        this.filePath = filePath;
        this.mapper = new ObjectMapper();
    }

    public <T> void save(T obj) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), obj);
    }

    public <T> T load(Class<T> clazz) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        return mapper.readValue(file, clazz);
    }
}