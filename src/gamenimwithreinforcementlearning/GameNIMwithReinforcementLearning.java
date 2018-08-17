/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamenimwithreinforcementlearning;

import gamenimwithreinforcementlearning.Level;
import gamenimwithreinforcementlearning.Node;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author YAFIE IMAM A
 */
public class GameNIMwithReinforcementLearning {
    static Scanner scan = new Scanner(System.in);
    static int root;
    static List<Level> levelList = new ArrayList<>();
    static int eksplorasi;
    static int eksploitasi;

    static int isFinished = 1;
    static int id_node = 0;
    static int pengulangan = 0;
    static double learning_rate;

    static int TURN = 1;
    static String mainLagi = "y";

    public static void main(String args[]){
        int jwb;

        root = Integer.parseInt(JOptionPane.showInputDialog("Masukkan Total Jumlah Batang!"));
        
        pengulangan = Integer.parseInt(JOptionPane.showInputDialog("Berapa kali pengulangan learning?"));
        
        eksplorasi = Integer.parseInt(JOptionPane.showInputDialog("Masukkan nilai eksplorasi(range 0-100)!"));
        eksploitasi = 100-eksplorasi;
        
        learning_rate = Double.parseDouble(JOptionPane.showInputDialog("Masukkan Learning Rate!"));

        List<Node> listNode = new ArrayList<>();
        List<Integer> nilai = new ArrayList<>();
        nilai.add(root);

        Node node = new Node();
        node.setId(++id_node);
        node.setNilai(nilai);
        node.setNilaiNode(0.5);
        node.setIdParent(0);
        node.setIdLevel(levelList.size());
        listNode.add(node);

        levelList.add(new Level(levelList.size(), listNode));

        possibilityOfAddition(root);

        while(isFinished>0){
            makeNewLevel();
        }
//        gamePlay();

        for(int i=0; i<pengulangan; i++){
            gamePlay();
        }

        System.out.print(levelList.toString());

//        realGame();

        realPlay();
    }

    static void makeNewLevel(){
        isFinished = 0;

//        System.out.println("makeNewLevel()");
        Level level = levelList.get(levelList.size()-1);
        List<Node> listNode = level.getNodeList();
        List<Node> newNodeList = new ArrayList<>();

        for(int j=0; j<listNode.size(); j++){
            Node node = listNode.get(j);
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

                            nodeTemp.setId(++id_node);
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
                            isFinished++;

                        }
                    }
                }
            }
        }

        levelList.add(new Level(levelList.size(), newNodeList));

//        for(Level level2: levelList){
//            System.out.println(level2.getNodeList().toString());
//        }
    }

    static void possibilityOfAddition(int number){
        List<Node> listNode = new ArrayList<>();
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
                node.setId(++id_node);
                node.setNilai(members);
                node.setNilaiNode(0.5);
                node.setIdLevel(levelList.size());

                listNode.add(node);

                existNumberList.add(i);
                existNumberList.add(temp);
            }
        }

        levelList.add(new Level(levelList.size(), listNode));

//        for(Level level: levelList){
//            System.out.print(level.getNodeList().toString());
//        }
    }

    static double calculateValueFunction(Node node){
        Node parentNode =  getNodeById(node.getIdParent());
        double vs = parentNode.getNilaiNode();
        double vsAxcent = node.getNilaiNode();
        vs = (vs + learning_rate * (vsAxcent-vs));
//        return (double)Math.round(vs * 1000) / 1000;
        return round(vs, 3);
    }


    static Node getNodeById(int idNode){
        Node node2 = new Node();
        for (int i=0; i< levelList.size(); i++){
            Level level = levelList.get(i);
            for (int j=0; j<level.getNodeList().size(); j++){
                Node node = level.getNodeList().get(j);
                if(node.getId() == idNode)
                    return node;
            }
        }
        return node2;
    }

    static void gamePlay(){
//        System.out.println("==============================\n");
        for(int i=0; i<levelList.size()-1; i++){
            Level level = getLevelById(i, levelList);
            List<Node> listNode = level.getNodeList();

//            System.out.println("nilai i : "+i);
//            System.out.println(listNode.toString());


            Node nodeExplore;
            if(isExplore()){
                //explore
                nodeExplore = getRandomItem(listNode);
            } else {
                //exploitasi
                int idNode = 0;

                if(listNode.size()==1){
                    idNode = listNode.get(0).getId();
                }

                for(int k=0; k<listNode.size()-1; k++){
                    if(k==0){
                        idNode = listNode.get(k).compareTo(listNode.get(k+1));
                    }
                    else {
                        Node nodeTempp = getNodeById(idNode);
                        idNode = listNode.get(k+1).compareTo(nodeTempp);
                    }
                }
                nodeExplore = getNodeById(idNode);
            }

            getNodeById(nodeExplore.getIdParent()).setNilaiNode(calculateValueFunction(nodeExplore));

            List<Integer> members = nodeExplore.getNilai();
            int isStop= 0;
            for(int p=0; p<members.size();p++){
                int member = members.get(p);
                if(member>2){
                    isStop++;
                }
            }
            if(isStop==0){
//                System.out.println("STOP\tLEVEL KE "+i);
                break;
            } else {
//                System.out.println("isKalah : "+isStop);
            }
        }

    }

    static Level getLevelById(int idLevel, List<Level> levelList){
        Level level1 = new Level();
        for (Level level : levelList){
            if(level.getIdLevel()== idLevel){
                return level;
            }
        }
        return level1;
    }

    static boolean isExplore(){
        Random r = new Random();
        int randomValue = r.nextInt(100 + 1 - 0) + 0;
        if (randomValue > eksplorasi) {
            //tidak Explorasi
//            System.out.println("tidak Explorasi, nilai random : " + randomValue);
            return false;
        } else {
            //Explorasi
//            System.out.println("Explorasi, nilai random : " + randomValue);
            return true;
        }
    }

    static Random rand = new Random();
    static <T> T getRandomItem(List<T> list) {
        return list.get(rand.nextInt(list.size()));
    }

    static List<Node> getNodesByIdNodeParent(int idNodeParent, List<Node> listNode){
        List<Node> listNodeTemp = new ArrayList<>();
        for(int i=0; i<listNode.size(); i++){
            Node node = listNode.get(i);
            if(node.getIdParent()==idNodeParent){
                listNodeTemp.add(node);
            }
        }
        return listNodeTemp;
    }


    static void realPlay() {
        eksplorasi = 0;
        int isStop = 0;
        
        int turn = Integer.parseInt(JOptionPane.showInputDialog("Pilih Main Pertama atau Kedua?\n1. Main Pertama\n2. Main Kedua"));
        if(turn==2)
            turn=0;

        int nodeIdTemp = 1;
        for(int i=1; i<levelList.size(); i++){
            Level level = levelList.get(i);
            List<Node> listNode = level.getNodeList();
            List<Node> listNodeTemp = getNodesByIdNodeParent(nodeIdTemp, listNode);

            //System.out.println(listNodeTemp.toString());
            if(listNodeTemp.size()==0){
                if(i%2==turn){
                    JOptionPane.showMessageDialog(null, "Maaf, Kamu Kalah!!");
                } else {
                    JOptionPane.showMessageDialog(null, "Selamat, Kamu Menang!!");
                }
                break;
            }

            if(i%2==turn){
                nodeIdTemp = Integer.parseInt(JOptionPane.showInputDialog(listNodeTemp.toString() + "\nPilih Langkah (ID) Selanjutnya"));
            } else {
                //nodeIdTemp = ;
                if(listNodeTemp.size()==1){
                    nodeIdTemp = listNodeTemp.get(0).getId();
                }
                for(int k=0; k<listNodeTemp.size()-1; k++){
                    if(k==0){
                        if(turn==1){
                            nodeIdTemp = listNodeTemp.get(k).compareToSmaller(listNodeTemp.get(k+1));
                        } else {
                            nodeIdTemp = listNodeTemp.get(k).compareTo(listNodeTemp.get(k+1));
                        }
                    }
                    else {
                        Node nodeTempp = getNodeById(nodeIdTemp);
                        if(turn==1){
                            nodeIdTemp = listNodeTemp.get(k+1).compareToSmaller(nodeTempp);
                        } else {
                            nodeIdTemp = listNodeTemp.get(k+1).compareTo(nodeTempp);
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, listNodeTemp.toString() + "\nAI Memilih langkah " + nodeIdTemp);
            }

            Node node = getNodeById(nodeIdTemp);
            List<Integer> members = node.getNilai();
            isStop = 0;
            for(int p=0; p<members.size();p++){
                int member = members.get(p);
                if(member>2){
//                    System.out.println("MEMBER list: "+members.toString());
                    isStop++;
                }
            }

            if(isStop==0){
                if(i%2==turn){
                    JOptionPane.showMessageDialog(null, "Selamat, Kamu Menang!!");
                } else {
                    JOptionPane.showMessageDialog(null, "Maaf, Kamu Kalah!!");
                }
                break;
            } else {
//                System.out.println("isStop : "+isStop+"\t LEVEL KE "+i);
            }
        }

    }

    static double round(double value, int places){
        if(places<0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
