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

        System.out.println("�����������ļ���D:/�����ʲ�/SceneRandGenʵ��/config.json��");
        BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));
        File config_file = new File(strin.readLine());
        JSONObject config;
        if(config_file.exists()){
            String input = FileUtils.readFileToString(config_file, "UTF-8");
            config= new JSONObject(input);
        }else {
            config = new JSONObject();
            System.out.println("�����뱾��·����D:/�����ʲ�/SceneRandGenʵ��/��ͥ.owl��");//
            String OntoPath= strin.readLine();
            config.put("owl�����ļ�·��",OntoPath);
            System.out.println("���������ɵ�ABox����·����D:/�����ʲ�/ABoxRandGenʵ�飩");
            String savePath=strin.readLine();
            config.put("���ɵı��屣��·��",savePath);
            System.out.println("�������������ABox�ĸ�����10��");
            int numAoxs= Integer.parseInt(strin.readLine());
            config.put("ABox����",numAoxs);
            System.out.println("������ÿ����������ɵ����ʵ��������8��");
            int numInstances= Integer.parseInt(strin.readLine());
            config.put("(ʵ��/��)���ֵ",numInstances);
            System.out.println("�����벻һ�¹���������3��");
            int numInconSets= Integer.parseInt(strin.readLine());
            config.put("��һ�¹��������Ŀ",numInconSets);
            System.out.println("�����벻һ�¹����Ƴ�������0.2��");
            double ratio= Double.parseDouble(strin.readLine());
            config.put("�Ƴ���һ�¹������",ratio);
            System.out.println("�Ƿ�����ģʽ��false/true��");
            boolean maxmad= Boolean.getBoolean(strin.readLine());
            config.put("������ģʽ",maxmad);
        }

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology tbox = manager.loadOntologyFromOntologyDocument(new File(config.getString("owl�����ļ�·��")));
        ShuffleScenes arg = new ShuffleScenes();
        for(int i=0;i<config.getInt("ABox����");i++) {
            arg.aboxRandGen(tbox, config.getInt("(ʵ��/��)���ֵ"),
                config.getDouble("�Ƴ���һ�¹������"),config.getInt("��һ�¹��������Ŀ"),
                config.getString("���ɵı��屣��·��")+"/tbox_plus_abox" + i,
                config.getBoolean("������ģʽ"));
            System.gc();
        }
    }

}
