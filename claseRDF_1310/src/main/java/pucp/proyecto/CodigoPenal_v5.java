import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import static org.apache.jena.atlas.iterator.Iter.iter;
import static org.apache.jena.enhanced.BuiltinPersonalities.model;
import org.apache.jena.enhanced.Personality;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;
import static org.apache.jena.vocabulary.DCAT.NS;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.VCARD;

public class NewClass1 {
    
    
public static void main(String[] args){
        String NS = "https://www.codigopenalperu/#";
        Model model = ModelFactory.createDefaultModel();
        relaciones(model, NS);
        descargarArchivo(model, "CodigoPenalFinal_v4");
    }

    public static void relaciones(Model model, String NS){
        //Ley Peruana y Código penal
        Resource leyPeruana = crearRecurso(NS, "LeyPeruana", model);
        Property tiene = crearPropiedad(NS, "tiene", model);
        Resource codigoPenal = crearRecurso(NS, "CodigoPenal", model);
        crearRelacion(model, leyPeruana, tiene, codigoPenal);
        //Libros de código penal
        Property nombre = crearPropiedad(NS, "nombre", model);
        Resource libro1 = crearRecurso(NS, "Libro1", model);
        Resource libro2 = crearRecurso(NS, "Libro2", model);
        Resource libro3 = crearRecurso(NS, "Libro3", model);
        agregarPropiedadARecurso(libro1, nombre, "DisposicionesGenerales");
        agregarPropiedadARecurso(libro2, nombre, "TipoDeDelito");
        agregarPropiedadARecurso(libro3, nombre, "Fallas");
        crearRelacion(model, codigoPenal, tiene, libro1);
        crearRelacion(model, codigoPenal, tiene, libro2);
        crearRelacion(model, codigoPenal, tiene, libro3);
        //titulo vida y salud
        Resource titulo1 = crearRecurso(NS,"VidaySalud", model);
        
        // Capítulo 1
        Resource capitulo1 = crearRecurso(NS, "Capitulo1", model);
        crearRelacion(model, libro2, tiene, titulo1);
        crearRelacion(model, titulo1,tiene, capitulo1);
        
        // Homicidios
        Resource homicidio = crearRecurso(NS, "Homicidio", model);
        crearRelacion(model, capitulo1, tiene, homicidio);

        Resource hcalificado = crearRecurso(NS, "H.Calificado", model);
        Resource hculposo = crearRecurso(NS, "H.Culposo", model);
        Resource hsimple = crearRecurso(NS, "H.Simple", model);
        Resource infanticidio = crearRecurso(NS, "Infaticidio", model);
        Resource isuicidio = crearRecurso(NS, "Inst.Suicidio", model);
        Resource hpiadoso = crearRecurso(NS, "H.Piadoso", model);
        Resource hviolenta = crearRecurso(NS, "H.E.Violenta", model);
        Resource parricidio = crearRecurso(NS, "Parricidio", model);
        Resource feminicidio = crearRecurso(NS, "Feminicidio", model);
        Resource sicariato = crearRecurso(NS, "Sicariato", model);
        Resource csicariato = crearRecurso(NS, "Consp.Sicariato", model);
        Resource cvictima = crearRecurso(NS, "CondicionVictima", model);

        defineSubClase(model, hcalificado, homicidio);
        defineSubClase(model, hculposo, homicidio);
        defineSubClase(model, hsimple, homicidio);
        defineSubClase(model, infanticidio, homicidio);
        defineSubClase(model, hpiadoso, homicidio);
        defineSubClase(model, hviolenta, homicidio);
        defineSubClase(model, isuicidio, homicidio);

        defineTipo(model, parricidio, hsimple);
        defineTipo(model, feminicidio, hcalificado);
        defineTipo(model, sicariato, hcalificado);
        defineTipo(model, csicariato, hcalificado);
        defineTipo(model, cvictima, hcalificado);

        // Propiedades
        // TODO: Agregar propiedades a libro 1
        
               
            // Victima
        Property victima = crearPropiedad(NS, "Victima", model);
            // Contexto
        Property contexto = crearPropiedad(NS, "Contexto", model);
        Property tiempo = crearPropiedad(NS, "Tiempo", model);
        Property espacio = crearPropiedad(NS, "Espacio", model);
        defineSubPropiedades(model, tiempo, contexto);
        defineSubPropiedades(model, espacio, contexto);
            // Pena
        Property pena = crearPropiedad(NS, "Pena", model);
        Property privativa = crearPropiedad(NS, "Privativa", model);
        Property restrictiva = crearPropiedad(NS, "Restrictiva", model);
        Property limitada = crearPropiedad(NS, "Limitada", model);
        Property multa = crearPropiedad(NS, "Multa", model);
        defineSubPropiedades(model, privativa, pena);
        defineSubPropiedades(model, restrictiva, pena);
        defineSubPropiedades(model, limitada, pena);
        defineSubPropiedades(model, multa, pena);
                // Limitada
        Property iAccesitoria = crearPropiedad(NS, "Inha.Accesitoria", model);
        Property diasLibresRes = crearPropiedad(NS, "DiasLibrsRestringidos", model);
        Property inhabilitacion = crearPropiedad(NS, "Inhabilitación", model);
        Property servicioComunitario = crearPropiedad(NS, "ServicioComunitario", model);
        defineSubPropiedades(model, iAccesitoria, limitada);
        defineSubPropiedades(model, diasLibresRes, limitada);
        defineSubPropiedades(model, inhabilitacion, limitada);
        defineSubPropiedades(model, servicioComunitario, limitada);
            // Punibilidad
        Property punibilidad = crearPropiedad(NS, "Punibilidad", model);
                // Base
        Property base = crearPropiedad(NS, "Base", model);
        defineSubPropiedades(model, base, punibilidad);
        Property falta = crearPropiedad(NS, "Falta", model);
        Property doloso = crearPropiedad(NS, "Doloso", model);
        Property omision = crearPropiedad(NS, "Omisión", model);
        Property error = crearPropiedad(NS, "Error", model);
        Property errorCultural = crearPropiedad(NS, "ErrorCultural", model);
        defineSubPropiedades(model, falta, base);
        defineSubPropiedades(model, doloso, base);
        defineSubPropiedades(model, omision, base);
        defineSubPropiedades(model, error, base);
        defineSubPropiedades(model, errorCultural, base);
                // Tentativa
        Property tentativa = crearPropiedad(NS, "Tentativa", model);
        defineSubPropiedades(model, tentativa, punibilidad);
        Property tentativapropia = crearPropiedad(NS, "TentativaPropia", model);
        Property impunidad = crearPropiedad(NS, "Impunidad", model);
        Property arrepentimientoActivo = crearPropiedad(NS, "ArrepentimientoActivo", model);
        Property varios = crearPropiedad(NS, "Varios", model);
        defineSubPropiedades(model, tentativapropia, tentativa);
        defineSubPropiedades(model, impunidad, tentativa);
        defineSubPropiedades(model, arrepentimientoActivo, tentativa);
        defineSubPropiedades(model, varios, tentativa);
                // Autoria
        Property autoria = crearPropiedad(NS, "Autoria", model);
        defineSubPropiedades(model, autoria, punibilidad);
        Property autor = crearPropiedad(NS, "Autor", model);
        Property coautor = crearPropiedad(NS, "CoAutor", model);
        Property instigador = crearPropiedad(NS, "Instigador", model);
        Property complice = crearPropiedad(NS, "Complice", model);
        Property incomunicabilidad = crearPropiedad(NS, "Incomunicabilidad", model);
        Property accionPorOtros = crearPropiedad(NS, "AccionPorOtros", model);
        defineSubPropiedades(model, autor, autoria);
        defineSubPropiedades(model, coautor, autoria);
        defineSubPropiedades(model, instigador, autoria);
        defineSubPropiedades(model, complice, autoria);
        defineSubPropiedades(model, incomunicabilidad, autoria);
        defineSubPropiedades(model, accionPorOtros, autoria);
                // Causa
        Property causa = crearPropiedad(NS, "Causa", model);
        defineSubPropiedades(model, causa, punibilidad);
        Property inimputable = crearPropiedad(NS, "Inimputable", model);
        Property responsabilidadRestringida = crearPropiedad(NS, "ResponsabilidadRestringida", model);
        Property responsabilidadRestringidaEdad = crearPropiedad(NS, "ResponsabilidadRestringidaEdad", model);
        defineSubPropiedades(model, inimputable, causa);
        defineSubPropiedades(model, responsabilidadRestringida, causa);
        defineSubPropiedades(model, responsabilidadRestringidaEdad, causa);
                // Aplicación de Pena
        Property aplicacionPena = crearPropiedad(NS, "AplicaciónPena", model);
        defineSubPropiedades(model, aplicacionPena, punibilidad);
        Property presupuesto = crearPropiedad(NS, "Presupuesto", model);
        Property circunstancias = crearPropiedad(NS, "Circunstancias", model);
        Property agravantes = crearPropiedad(NS, "Agravantes", model);
        Property atenuantes = crearPropiedad(NS, "Atenuantes", model);
        defineSubPropiedades(model, presupuesto, aplicacionPena);
        defineSubPropiedades(model, circunstancias, aplicacionPena);
        defineSubPropiedades(model, agravantes, circunstancias);
        defineSubPropiedades(model, atenuantes, circunstancias);
        
        Property usoDeMenor = crearPropiedad(NS, "UsoDeMenor", model);
        Property habitualidad = crearPropiedad(NS, "Habitualidad", model);
        Property reincidentes = crearPropiedad(NS, "Reincidentes", model);
        Property parentesco = crearPropiedad(NS, "Parentesco", model);
        defineSubPropiedades(model, usoDeMenor, agravantes);
        defineSubPropiedades(model, habitualidad, agravantes);
        defineSubPropiedades(model, reincidentes, agravantes);
        defineSubPropiedades(model, parentesco, agravantes);
        
        crearRelacion(model, libro1, tiene, contexto);
        crearRelacion(model, libro1, tiene, pena);
        crearRelacion(model, libro1, tiene, punibilidad);
        crearRelacion(model, libro1, tiene, victima);
        
        crearRelacion(model, parricidio,agravantes,parentesco);
        crearRelacion(model, parricidio,tiene,atenuantes);
        
        //Resource quince = crearRecurso(NS, "15 años", model);
        model.add(circunstancias, RDFS . subClassOf, aplicacionPena);
        model.add(agravantes, RDFS.domain, circunstancias);
        Resource padre = crearRecurso(NS, "padre", model);
        Resource veinticinco = crearRecurso (NS,"25 años", model);
        
        //model.add(quince,atenuantes,padre);
        model.add(veinticinco,agravantes,padre);
        
        // Hechos
        Resource hecho = crearRecurso(NS, "Hecho", model);
        Resource victimario = crearRecurso(NS, "Victimario", model);
            //victimario
        Resource vicImplicado = crearRecurso(NS, "Implicado", model);
        Resource vicDirecto = crearRecurso(NS, "Directo", model);
        Resource victimah = crearRecurso(NS, "Victima", model);
        //propiedades de Hecho
        Property causa1 = crearPropiedad(NS, "Causa", model);
        Property AccionAdjetivo = crearPropiedad(NS, "AcciónCalificativo", model);
            
         
        defineSubClase(model, vicDirecto, victimario);
        defineSubClase(model, vicImplicado, victimario);
        defineTipo(model, causa,hecho);
        defineTipo(model, AccionAdjetivo,hecho);
        defineTipo(model, victimah,hecho);
                   
    
           String inputFileName = "CodigoPenalFinal_v4.rdf";
//        Model model = FileManager.get().loadModel(inputFileName);
           InfModel inf = ModelFactory.createRDFSModel(model);
           String resourceURI = NS + "padre";
           //Resource padre = model.getResource(resourceURI);
           resourceURI = NS +"parricidio";
           //Resource parricidio = model.getResource(resourceURI);

            Selector selector = new SimpleSelector(padre, RDF.type, parricidio);
            StmtIterator iter = inf.listStatements(selector); 
    
            while (iter.hasNext()) {
            System.out.println(iter.nextStatement().toString());
            }    
        
        
    }
    
     

    public static void agregarPropiedadARecurso(Resource resource, Property property, String value){
        resource.addProperty(property, value);
    }

    public static void crearRelacion(Model model, Resource inputResource, Property property, Resource outputResource){
        model.add(inputResource, property, outputResource);
    }

    public static void defineTipo(Model model, Resource childResource, Resource parentResource) {
        model.add(childResource, RDF.type, parentResource);
    }

    public static void defineSubClase(Model model, Resource childResource, Resource parentResource) {
        model.add(childResource, RDFS.subClassOf, parentResource);
    }

    public static void defineSubPropiedades(Model model, Property childProp, Property parentProp) {
        model.add(childProp, RDFS.subPropertyOf, parentProp);
    }

    private static Property crearPropiedad(String NS, String id, Model model) {
        String propertyURI = NS + id;
        return model.createProperty(propertyURI);
    }

    private static Resource crearRecurso(String NS, String id, Model model) {
        String resourceURI = NS + id;
        return model.createResource(resourceURI);
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
    
