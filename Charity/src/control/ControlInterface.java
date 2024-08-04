/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package control;

/**
 *
 * @author Ooi Choon Chong
 * @param <T>
 */
public interface ControlInterface<T> {
    
    public void     display(T newEntry);
    public boolean  create(T newEntry);
    public boolean  remove(T newEntry);
    public boolean  update(T newEntry);
    public boolean  report(T newEntry);
    
}
