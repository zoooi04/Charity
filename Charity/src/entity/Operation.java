/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Chew Huai Ern
 * @param <T>
 */
public class Operation<T> {
    private Type type;
    private T data;
    private T previousData; // Used for update operation to store previous state

    
    public enum Type {
        CREATE, DELETE, UPDATE
    }


    public Operation(Type type, T data) {
        this.type = type;
        this.data = data;
    }

    public Operation(Type type, T data, T previousData) {
        this.type = type;
        this.data = data;
        this.previousData = previousData;
    }

    public Type getType() {
        return type;
    }

    public T getData() {
        return data;
    }

    public T getPreviousData() {
        return previousData;
    }
}
