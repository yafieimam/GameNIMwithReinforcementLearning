/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nimgame;

import gamenimwithreinforcementlearning.Level;
import gamenimwithreinforcementlearning.Node;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author YAFIE IMAM A
 */
public class GameNIMwithReinforcementLearning {
    static int root, ulang, eksplorasi, eksploitasi;
    static double learning_rate;
    static int jumlah = 0;
    static List<Level> levelList = new ArrayList<>();
    static int node_selesai= 1;
    
    static void makeNewLevel(){
        node_selesai = 0;

//        System.out.println("makeNewLevel()");
        Level level = levelList.get(levelList.size()-1);
        List<Node> nodeList = level.getNodeList();
        List<Node> newNodeList = new ArrayList<>();

        for(int j=0; j<nodeList.size(); j++){
            Node node = nodeList.get(j);
            List<Integer> members = node.getNilai();
            for(Integer member : members){
                List<Integer> existNumberList = new ArrayList<>();
                if(member > 2){
                    //maka pecah buat node baru
                    for(int i=member; i>0; i--){
                        int temp = member-i;
                        if((2*temp == member) || existNumberList.contains(i) || existNumberList.contains(temp) || temp == 0){
                            //tidak bisa mecah
                            // karena member tidak boleh dipecah dengan membagi 2
                        } else {
                            List<Integer> membersTemp = new ArrayList<>();
                            membersTemp.addAll(members);
                            membersTemp.remove(member);
                            membersTemp.add(temp);
                            membersTemp.add(i);

                            Node nodeTemp = new Node();

                            existNumberList.add(i);
                            existNumberList.add(temp);

                            nodeTemp.setId(++jumlah);
                            nodeTemp.setNilai(membersTemp);

                            int isMenang = 0;
                            for(Integer member1 : membersTemp){
                                if(member1>2)
                                    isMenang++;
                            }
                            if(isMenang==0){
                                if(levelList.size()%2 == 1){
                                    //ganjil => menang
//                                    System.out.println("MENANG");
                                    nodeTemp.setNilaiNode(1);
                                } else {
                                    //genap => kalah
                                    nodeTemp.setNilaiNode(0);
//                                    System.out.println("KALAH");
                                }
                            } else
                                nodeTemp.setNilaiNode(0.5);

                            nodeTemp.setIdParent(node.getId());
                            nodeTemp.setIdLevel(levelList.size());

                            newNodeList.add(nodeTemp);
                            node_selesai++;

                        }
                    }
                }
            }
        }

        levelList.add(new Level(levelList.size(), newNodeList));

//        for(Level level2: LEVEL_LIST){
//            System.out.println(level2.getNodeList().toString());
//        }
    }

    static void possibilityOfAddition(int number){
        List<Node> nodeList = new ArrayList<>();
        List<Integer> existNumberList = new ArrayList<>();
        for(int i=1; i<number;i++){
            int temp = number-i;
            //check exist
            if (existNumberList.contains(i)) {
//                System.out.println("number : "+i+" Exist");
            } else {
                Node node = new Node();
                List<Integer> members = new ArrayList<>();
                members.add(i);
                members.add(temp);

                node.setIdParent(1);
//                node.setId(++);
                node.setNilai(members);
                node.setNilaiNode(0.5);
                node.setIdLevel(levelList.size());

                nodeList.add(node);

                existNumberList.add(i);
                existNumberList.add(temp);
            }
        }

        levelList.add(new Level(levelList.size(), nodeList));

//        for(Level level: LEVEL_LIST){
//            System.out.print(level.getNodeList().toString());
//        }
    }
    
//    public void buatNode(int root){
//        List<Node> listNode = new ArrayList<>();
//        List<Integer> nilaiTemp = new ArrayList<>();
//        for(int i=1; i<root; i++){
//            int temp = root-i;
//            if((nilaiTemp.contains(i)) || (temp*2 == root) || (nilaiTemp.contains(temp))){
//                continue;
//            }else{
//                List<Integer> nilai = new ArrayList<>();
//                Node node = new Node();
//                nilai.add(i);
//                nilai.add(temp);
//                
//                jumlah++;
//                
//                node.setId(jumlah);
//                node.setNilai(nilai);
//                node.setNilaiNode(0.5);
//                node.setIdParent(1);
//                node.setIdLevel(levelList.size());
//                listNode.add(node);
//                
//                nilaiTemp.add(i);
//                nilaiTemp.add(temp);
//            }
//        }
//        Level level = new Level();
//        level.setIdLevel(levelList.size());
//        level.setNodeList(listNode);
//        levelList.add(level);
//        
//        for(Level level2: levelList){
//            System.out.println(level2.getNodeList().toString());
//        }
//    }
//    
//    public void buatLevel(){
//        node_selesai = 0;
//        System.out.println("===============================");
//        Level level = levelList.get(levelList.size()-1);
//        List<Node> listNode = level.getNodeList();
//        List<Node> nodeTemp = new ArrayList<>();
//        
//        for(int i=0; i<listNode.size(); i++){
//            Node node = listNode.get(i);
//            List<Integer> nilai = node.getNilai();
//            for(Integer n : nilai){
//                List<Integer> nilaiTemp = new ArrayList<>();
//                if(n > 2){
//                    for(int j=n; j>0; j--){
//                        int temp = n - j;
//                        if((temp == 0) || (temp*2 == n) || (nilaiTemp.contains(j)) || (nilaiTemp.contains(temp))){
//                            continue;
//                        }else{
//                            List<Integer> nilai2 = new ArrayList<>();
//                            nilai2.addAll(nilai);
//                            nilai2.remove(n);
//                            nilai2.add(j);
//                            nilai2.add(temp);
//                            
//                            Node node2 = new Node();
//                            jumlah++;
//                            node2.setId(jumlah);
//                            node2.setNilai(nilai2);
//                            
//                            int win = 0;
//                            for(Integer nilai3 : nilai2){
//                                if(nilai3>2)
//                                    win++;
//                            }
//                            if(win==0){
//                                if(levelList.size()%2 == 1){
//                                    node2.setNilaiNode(1);
//                                } else {
//                                    node2.setNilaiNode(0);
//                                }
//                            } else{
//                                node2.setNilaiNode(0.5);
//                            }
//                            
//                            node2.setIdParent(node.getId());
//                            node2.setIdLevel(levelList.size());
//                            nodeTemp.add(node2);
//                            
//                            nilaiTemp.add(j);
//                            nilaiTemp.add(temp);
//                            node_selesai++;
//                        }
//                    }
//                }
//            }
//        }
//        Level level2 = new Level();
//        level2.setIdLevel(levelList.size());
//        level2.setNodeList(nodeTemp);
//        levelList.add(level2);
//        
//        for(Level level3: levelList){
//            System.out.println(level3.getNodeList().toString());
//        }
//    }
    
    public Level getLevel(int id, List<Level> listLevel){
        Level level = new Level();
        for(Level level1 : listLevel){
            if(level1.getIdLevel() == id){
                return level1;
            }
        }
        return level; 
    }
    
    static Node getNode(int id){
        Node node = new Node();
        for(int i=0; i<levelList.size(); i++){
            Level level = levelList.get(i);
            for(int j=0; j<level.getNodeList().size(); j++){
                Node node2 = level.getNodeList().get(j);
                if(node2.getId() == id){
                    return node2;
                }
            }
        }
        return node;
    }
    
    static double round(double value, int places){
        if(places<0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public double hitungNilaiNode(Node node){
        Node parent = getNode(node.getIdParent());
        double nilai_parent = parent.getNilaiNode();
        double nilai = node.getNilaiNode();
        nilai_parent = (nilai_parent + learning_rate * (nilai - nilai_parent));
        return round(nilai_parent,3);
    }
    
    public void reinforcementLearning(){
        Random random = new Random();
        for(int i=0; i<levelList.size()-1; i++){
            Level level = getLevel(i, levelList);
            List<Node> listNode = level.getNodeList();
            int nilai_random = random.nextInt(100 + 1 - 0) + 0;
            System.out.println("Nilai random = " + nilai_random);
            Node node_eksplorasi;
            if(nilai_random < eksplorasi){
                node_eksplorasi = listNode.get(random.nextInt(listNode.size()));
            }else{
                int id = 0;
   
                if(listNode.size() == 1){
                    id = listNode.get(0).getId();
                }
                for(int j=0; j<listNode.size()-1; j++){
                    if(j == 0){
                        id = listNode.get(j).compareTo(listNode.get(j+1));
                    }else{
                        Node node_temp;
                        node_temp = getNode(id);
                        id = listNode.get(j+1).compareTo(node_temp);
                    }
                }
                node_eksplorasi = getNode(id);
            }
            getNode(node_eksplorasi.getIdParent()).setNilaiNode(hitungNilaiNode(node_eksplorasi));
            System.out.println("Nilai Parent = " + hitungNilaiNode(node_eksplorasi));
            List<Integer> nilai = node_eksplorasi.getNilai();
            int berhenti = 0;
            for(int j=0; j<nilai.size(); j++){
                int nilai_temp = nilai.get(j);
                if(nilai_temp > 2){
                    berhenti++;
                }
            }
            if(berhenti == 0){
                break;
            }
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        int root = Integer.parseInt(JOptionPane.showInputDialog("Masukkan Total Jumlah Batang!"));
        int ulang = Integer.parseInt(JOptionPane.showInputDialog("Berapa kali pengulangan learning?"));
        int eksplorasi = Integer.parseInt(JOptionPane.showInputDialog("Masukkan nilai eksplorasi(range 0-100)!"));
        int eksploitasi = 100-eksplorasi;
        double learning_rate = Double.parseDouble(JOptionPane.showInputDialog("Masukkan Learning Rate!"));
        System.out.println("LEARNING RATE = " + learning_rate);
        List<Integer> nilai = new ArrayList<>();
        List<Node> listNode = new ArrayList<>();
        Node node = new Node();
        GameNIMwithReinforcementLearning gameNim = new GameNIMwithReinforcementLearning();
        Level level = new Level();
        
        nilai.add(root);
        node.setId(gameNim.jumlah);
        node.setNilai(nilai);
        node.setNilaiNode(0.5);
        node.setIdParent(0);
        node.setIdLevel(levelList.size());
        listNode.add(node);
        
        level.setIdLevel(levelList.size());
        level.setNodeList(listNode);
        levelList.add(level);
        
        listNode.get(0).toString();
        
        possibilityOfAddition(root);
        
        while(gameNim.node_selesai > 0){
            makeNewLevel();
        }
        
        for(int i=0; i<ulang; i++){
            gameNim.reinforcementLearning();
        }
        
        System.out.println("\nHASIL LEARNING");
        System.out.println("==================================\n");
        System.out.println(levelList.toString());
    }
    
}
