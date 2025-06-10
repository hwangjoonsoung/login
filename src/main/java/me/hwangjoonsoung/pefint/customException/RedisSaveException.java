package me.hwangjoonsoung.pefint.customException;

public class RedisSaveException extends RuntimeException{
    public RedisSaveException(String message) {
        super(message);
    }
}
