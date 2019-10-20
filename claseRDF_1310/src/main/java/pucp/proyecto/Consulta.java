/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucp.proyecto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.util.FileManager;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDFS;

/**
 *
 * @author HP
 */
public class Consulta {

    static String NS = "https://www.codigopenalperu/#";
    static Model model = leerModelo();
    static InfModel inf = ModelFactory.createRDFSModel(model);
    static Resource recurso = null;

    public static void main(String[] args) {
        //leerModelo();
        //inf = ModelFactory.createRDFSModel(model);

        setDatos();
        getTodosLosDelitos();
        //consultar();
    }

    public static void setDatos() {

        Property CondicionNormal = model.getProperty(NS + "CondicionNormal");
        Property CondicionAgravante = model.getProperty(NS + "CondicionAgravante");

        String CondicionNormal1 = "Familiar";
        String CondicionAgravante1 = "PorFerocidadLucroCodiciaPlacer";
        String CondicionAgravante2 = "ParaFacilitarOcultarOtroDelito";
        String CondicionAgravante3 = "ConCrueldadOAlevosia";
        String CondicionAgravante4 = "PorFuegoExplosionUOtroMedio";

        if (cumpleCondicion(CondicionNormal, CondicionNormal1)) {
            System.out.println("Aplicar pena sin agravante");
            System.out.println("POSIBLE DELITO: " + recurso);

            if (cumpleCondicion(CondicionAgravante, CondicionAgravante1)
                    || cumpleCondicion(CondicionAgravante, CondicionAgravante2)
                    || cumpleCondicion(CondicionAgravante, CondicionAgravante3)
                    || cumpleCondicion(CondicionAgravante, CondicionAgravante4)) {
                System.out.println("Aplicar pena con agravante");

            }
        } else {
            //System.out.println("No es Parricidio");
        }

    }

    public static boolean cumpleCondicion(Property condicion, String nombreCondicion) {
        boolean cumple = false;
        ResIterator resIter = model.listSubjectsWithProperty(condicion);
        while (resIter.hasNext()) {
            Resource res = resIter.nextResource();
            String condicionEncontrada = res.getProperty(condicion).getString();
            cumple = nombreCondicion.equals(condicionEncontrada);
            if (cumple) {
                recurso = res;
            }
            //System.out.println("Cumple condicion: " + cumple);
        }

        return cumple;
    }

    public static void consultar() {

        System.out.println("Consultando::");
        String resourceURI = NS + "Pepe";
        Resource Victima = model.createResource(resourceURI);

        resourceURI = NS + "PepeJr";
        Resource Victimario = model.createResource(resourceURI);

        String propertyURI = NS + "FamiliarDe";
        Property EsPadreDe = model.getProperty(propertyURI);

        Selector selector = new SimpleSelector(Victima, EsPadreDe, Victimario);
        //StmtIterator iter = model.listStatements(selector);
        StmtIterator iter = inf.listStatements(selector);

        while (iter.hasNext()) {
            System.out.println("******");
            System.out.println(iter.nextStatement().toString());
        }
    }

    public static void getTodosLosDelitos() {

        Resource delito= model.getResource(NS+"Homicidio");
        Selector selector = new SimpleSelector(null, RDFS.subClassOf, delito);// .listSubjectsWithProperty(condicion);
        StmtIterator resIter =inf.listStatements(selector);
        
        while (resIter.hasNext()) {
            String res = resIter.nextStatement().toString();
            System.out.println("TIPOS DE DELITOS " + res);
        }
    }

    public static Model leerModelo() {
        String inputFileName = "CodigoPenalFinal_v4.rdf";
        Model modelo = FileManager.get().loadModel(inputFileName);
        return modelo;

    }

    private static void descargarArchivo(Model model, String nombreArchivo) {
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(nombreArchivo + ".rdf");
        } catch (FileNotFoundException e) {
            System.out.println("Ocurrio un error al crear el archivo.");
        }
        model.write(output, "RDF/XML-ABBREV");
    }
}
