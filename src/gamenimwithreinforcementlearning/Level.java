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
public class Level {
    int id;
    List<Node> listNode;
    
    public Level(){}
    
    public Level(int id, List<Node> nodeList) {
        this.id = id;
        this.listNode = nodeList;
    }
    
    public void setIdLevel(int id){
        this.id = id;
    }
    
    public void setNodeList(List<Node> listNode){
        this.listNode = listNode;
    }
    
    public int getIdLevel(){
        return id;
    }
    
    public List<Node> getNodeList(){
        return listNode;
    }
    
    @Override
    public String toString() {
        return  listNode + "\n";
    }
}
