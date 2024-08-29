/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 *
 * @author Ooi Choon Chong
 */
public interface StackInterface<T> {

    void push(T newEntry);

    T pop();

    T peek();

    boolean isEmpty();

    void clear();
}
