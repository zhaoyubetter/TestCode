package groovy.better.com.groovytest

import groovy.xml.QName
import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginImpl implements Plugin<Project>{

    @Override
    void apply(Project project) {
        project.task('recordTask') << {
            println "recordTask start!"
            def items1=scanBuildLayoutItems(project)

            def items2=[]
            def element=new XmlParser().parse(new File(project.buildDir,"/intermediates/res/merged/debug/values/values.xml"))
            def findItems=element.item.findAll{ it.attributes().findAll{k,v-> 'id'.equals(v) } }
            findItems.forEach{ Node node-> items2<<node.attributes()["name"] }

            items1?.removeAll(items2)
            items1.addAll(items2)
            //update items
            updateItems(project,items1)
            println "recordTask end!"
        }
    }
    /**
     * 更新配置项
     * @param project
     * @param items
     */
    private void updateItems(project,items) {
        //检测assets目录配置文件
        def assetsFile = new File(project.name, "/src/main/assets")
        if (!assetsFile.exists()) {
            assetsFile.mkdir();
        }
        def oldItems=[:]
        def configFile = new File(assetsFile, "define_id.xml")
        if (configFile.exists()) {
            def element = new XmlParser().parse(configFile)
            element.item.each{
                oldItems<<[(it.attribute("id")):(it.attribute("info"))]
            }
        }
        def set=items as Set
        def finalSet=set
        def finalItems=new HashMap(oldItems)
        finalItems?.each{k,v-> set -= k }
        finalSet?.each { oldItems.remove(it) }
        finalItems-=oldItems//移除无用的define_id内id项

        def fileWriter=new FileWriter(configFile)
        def xml = new groovy.xml.MarkupBuilder(fileWriter)
        xml.define([ct:new Date().toLocaleString()]){
            set?.each{ item([id:it,info:""]) }
            finalItems.each {k,v->item([id:k,info:v])}
        }
    }
    /**
     * 扫描所有编译后的layout文件
     */
    def scanBuildLayoutItems(project) {
        def items=[]
        File file = new File(project.buildDir, "/intermediates/res/merged/debug/layout");
        if (file.exists()) {
            file.listFiles()?.each { item ->
                scanNode(new XmlParser().parse(new File(file, item.name)),items)
            }
        }
        items
    }

    /**
     * 扫描指定节点
     * @param node
     * @return
     */
    def scanNode(node,items){
        node.attributes()?.each {
            if(it.key instanceof QName){
                def name=it.key as QName;
                if(name.localPart?.is("id")){
                    def matcher=it.value=~/@(\+?id|\w+:id)\\/(\w+)/
                    if(matcher){
                        items<<matcher[0][2]
                    } else {
                        println "$it.value $matcher"
                    }
                }
            }
        }
        node.children()?.each { scanNode(it,items) }
    }
}