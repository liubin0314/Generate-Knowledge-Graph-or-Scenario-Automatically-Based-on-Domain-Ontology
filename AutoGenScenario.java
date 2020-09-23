package scenario_auto_gen;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class AutoGenScenario {

    public static void main(String args[]) throws OWLOntologyCreationException, OWLOntologyStorageException, IOException, InterruptedException {

        System.out.println("请输入配置文件（D:/数据资产/SceneRandGen实验/config.json）");
        BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));
        File config_file = new File(strin.readLine());
        JSONObject config;
        if(config_file.exists()){
            String input = FileUtils.readFileToString(config_file, "UTF-8");
            config= new JSONObject(input);
        }else {
            config = new JSONObject();
            System.out.println("请输入本体路径（D:/数据资产/SceneRandGen实验/家庭.owl）");//
            String OntoPath= strin.readLine();
            config.put("owl本体文件路径",OntoPath);
            System.out.println("请输入生成的ABox保存路径（D:/数据资产/ABoxRandGen实验）");
            String savePath=strin.readLine();
            config.put("生成的本体保存路径",savePath);
            System.out.println("请输入随机生成ABox的个数（10）");
            int numAoxs= Integer.parseInt(strin.readLine());
            config.put("ABox个数",numAoxs);
            System.out.println("请输入每个类随机生成的最大实例个数（8）");
            int numInstances= Integer.parseInt(strin.readLine());
            config.put("(实例/类)最大值",numInstances);
            System.out.println("请输入不一致公理集个数（3）");
            int numInconSets= Integer.parseInt(strin.readLine());
            config.put("不一致公理集最大数目",numInconSets);
            System.out.println("请输入不一致公理移除比例（0.2）");
            double ratio= Double.parseDouble(strin.readLine());
            config.put("移除不一致公理比例",ratio);
            System.out.println("是否开启狂暴模式（false/true）");
            boolean maxmad= Boolean.getBoolean(strin.readLine());
            config.put("开启狂暴模式",maxmad);
        }

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology tbox = manager.loadOntologyFromOntologyDocument(new File(config.getString("owl本体文件路径")));
        ShuffleScenes arg = new ShuffleScenes();
        for(int i=0;i<config.getInt("ABox个数");i++) {
            arg.aboxRandGen(tbox, config.getInt("(实例/类)最大值"),
                config.getDouble("移除不一致公理比例"),config.getInt("不一致公理集最大数目"),
                config.getString("生成的本体保存路径")+"/tbox_plus_abox" + i,
                config.getBoolean("开启狂暴模式"));
            System.gc();
        }
    }

}
