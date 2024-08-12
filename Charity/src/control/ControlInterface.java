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
    
    public boolean  create(T newEntry);               // add
    public boolean  remove(T newEntry);               // delete
    public boolean  update(T newEntry);               // edit (active inActive)
    public boolean  search(T newEntry, T newObject);  // find
    public void     display(T newEntry);              // show
    public boolean  report(T newEntry);               // report
    
}
