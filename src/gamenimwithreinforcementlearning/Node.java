/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamenimwithreinforcementlearning;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author YAFIE IMAM A
 */
public class Node implements Comparable{
    int id, idParent, idLevel, idChild;
    List<Integer> nilai;
    double nilai_node;
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setIdParent(int idParent){
        this.idParent = idParent;
    }
    
    public void setIdLevel(int idLevel){
        this.idLevel = idLevel;
    }
    
    public void setIdChild(int idChild){
        this.idChild = idChild;
    }
    
    public void setNilai(List<Integer> nilai){
        this.nilai = nilai;
    }
    
    public void setNilaiNode(double nilai_node){
        this.nilai_node = nilai_node;
    }
    
    public int getId(){
        return id;
    }
    
    public int getIdParent(){
        return idParent;
    }
    
    public int getIdLevel(){
        return idLevel;
    }
    
    public int getIdChild(){
        return idChild;
    }
    
    public double getNilaiNode(){
        return nilai_node;
    }
    
    public List<Integer> getNilai(){
        return nilai;
    }

    @Override
    public String toString() {
        return  "ID=" + id +
                " Langkah=" + nilai +
                " Nilai=" + nilai_node +
                '\n';
    }

    @Override
    public int compareTo(Object t) {
       Node node = (Node) t;
       if(this.nilai_node > node.getNilaiNode()){
           return getId();
       }else{
           return node.getId();
       }
    }
    
    public int compareToSmaller(Object t) {
       Node node = (Node) t;
       if(this.nilai_node < node.getNilaiNode()){
           return getId();
       }else{
           return node.getId();
       }
    }
}
