/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import entity.Donation;
import entity.Donor;
import entity.Event;

/**
 *
 * @author huaiern
 */
public class Test {
    public static void main(String[] args){
        //for every donation map event to donor
        WeightedBipartiteGraph<Event, Donor> graph = new WeightedBipartiteGraph<>(Event.class,Donor.class);
        //Event e1 = new Event("E1001")
        Event e1 = new Event();
        e1.setName("Cancer Fundraising Event");
        e1.setLocation("Cancer Region");
        //WeightedBipartiteGraph<Event,Donor>.Vertex<Event> vE = (WeightedBipartiteGraph<Event,Donor>.Vertex<Event>) graph.constructVertex(e1);
        System.out.println("Add event vertex: " + graph.addVertex(e1));
        
        //System.out.println("Type of v.data: " + e1.getClass().getName());
        
        
        Donor d1 = new Donor();
        d1.setName("Andrew");
        Donor d2 = new Donor();
        d2.setName("Jian Hui");
        //WeightedBipartiteGraph<Event, Donor>.Vertex<Donor> vD = (WeightedBipartiteGraph<Event,Donor>.Vertex<Donor>)graph.constructVertex(d1);
        System.out.println("Add donor vertex: " + graph.addVertex(d1));
        
        Event e2 = new Event();
        e2.setName("Challenge Hunger Event");
        
        graph.addEdge(e1, d1, 2000);//weight=donation amount
        graph.addEdge(e1, d2, 3000);//weight=donation amount
        graph.addEdge(e2, d2, 3000);//weight=donation amount
        graph.addEdge(e1, d1, 5000);//weight=donation amount
        System.out.println(graph.removeEdge(e1,d1));
        
        //graph.test(e1);
        graph.printGraph();//depends on entity toString()
    }
}
