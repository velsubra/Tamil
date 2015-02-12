package my.interest.tamil.rest.resources;

import my.interest.lang.tamil.internal.api.DefinitionFactory;

import my.interest.lang.tamil.generated.types.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Path("compound-word")
public class VerbCoumpoindWordResource {



    @GET
    @Path("/meaning.english/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<GenericTenseTable>  createEnglish(@PathParam("name") String name, @QueryParam("transitive") boolean transitive) {
        return new ObjectFactory().createGenericTable(new GenericTenseTable());
    }


    @GET
    @Path("/thozhirrpeyar/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<GenericTenseTable>  createThohirpeyar(@PathParam("name") String name, @QueryParam("transitive") boolean transitive) {
        return new ObjectFactory().createGenericTable(DefinitionFactory.generateThozhirPeyar(name, transitive));
    }




    @GET
    @Path("/vinaiyaalanaiyum-peyar/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<GenericTenseTable>  createVinaiyaalanaiyumPeyar(@PathParam("name") String name, @QueryParam("transitive") boolean transitive) {
        return new ObjectFactory().createGenericTable(DefinitionFactory.generateVinaiyaalanaiyumPeyar(name, transitive,false));
    }



    @GET
    @Path("/ethirmarrai-vinaiyaalanaiyum-peyar/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<GenericTenseTable>  createEthirVinaiyaalanaiyumPeyar(@PathParam("name") String name, @QueryParam("transitive") boolean transitive) {
        return new ObjectFactory().createGenericTable(DefinitionFactory.generateVinaiyaalanaiyumPeyar(name, transitive,true));
    }

    @GET
    @Path("/vinaimuttu/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<GenericTenseTable>  createVinaiMutruTable(@PathParam("name") String name, @QueryParam("transitive") boolean transitive) {
        return new ObjectFactory().createGenericTable(DefinitionFactory.generateVinaimuttu("vinaimuttu", name, transitive));
    }


    @GET
    @Path("/peyarechcham/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<GenericTenseTable>  createPeyarechchaTable(@PathParam("name") String name, @QueryParam("transitive") boolean transitive) {
        return new ObjectFactory().createGenericTable(DefinitionFactory.generatePeyarechcham(name, transitive));
    }


    @GET
    @Path("/vinaiyechcham/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<GenericTenseTable>  createVinaiyechchaTable(@PathParam("name") String name, @QueryParam("transitive") boolean transitive) {
        GenericTenseTable table = DefinitionFactory.generateVinaiyecham(name, transitive);
        if (!table.getRows().isEmpty()) {
            GenericTenseTable kad1 = DefinitionFactory.generateKaddalhai(name, transitive,"kaddalhai_ddum");
            GenericTenseTable kad2 = DefinitionFactory.generateKaddalhai(name, transitive,"kaddalhai_laam");

            table.getRows().add(kad1.getRows().get(0));
            table.getRows().add(kad2.getRows().get(0));


        }
        return new ObjectFactory().createGenericTable(table);
    }


    @GET
    @Path("/thodar-vinaimuttu/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<GenericTenseTable>  createThodarVinaimuttuTable(@PathParam("name") String name, @QueryParam("transitive") boolean transitive) {
        return new ObjectFactory().createGenericTable(DefinitionFactory.generateVinaimuttu("thodar-vinaimuttu", name, transitive));
    }


    @GET
    @Path("/muttu-vinaimuttu/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<GenericTenseTable>  createMuttuVinaimuttuTable(@PathParam("name") String name, @QueryParam("transitive") boolean transitive) {
        return new ObjectFactory().createGenericTable(DefinitionFactory.generateVinaimuttu("muttu-vinaimuttu", name, transitive));
    }


    @GET
    @Path("/thodar-muttu-vinaimuttu/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<GenericTenseTable>  createThodarMuttuVinaimuttuTable(@PathParam("name") String name, @QueryParam("transitive") boolean transitive) {
        return new ObjectFactory().createGenericTable(DefinitionFactory.generateVinaimuttu("thodar-muttu-vinaimuttu", name, transitive));
    }



    @GET
    @Path("/ethirmarrai-peyarechcham/name/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JAXBElement<GenericTenseTable>  createEthirMaraiPeyarechcham(@PathParam("name") String name, @QueryParam("transitive") boolean transitive) {
        return new ObjectFactory().createGenericTable(DefinitionFactory.generateVinaimuttu("ethirmarrai-peyarechcham", name, transitive, "எதிர்மரறைப்பெயரெச்சம்", true));
    }



}
