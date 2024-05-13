package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;

import modelo.ModeloEstado;
import modelo.ModeloEstadoSubasta;
import modelo.ModeloOferta;
import modelo.ModeloPiezas;
import modelo.ModeloSubasta;
import modelo.ModeloUsuario;
import modelo.PersistenciaOfertas;

public class LogicaOfertas {
    PersistenciaOfertas persistenciaOfertas = new PersistenciaOfertas();
    LogicaUsuarios logicaUsuarios = new LogicaUsuarios();
    // LogicaSubastas logicaSubastas = new LogicaSubastas();
    LogicaPiezas logicaPiezas = new LogicaPiezas();

    public Integer cargarDatos(String ruta) throws FileNotFoundException, IOException {
        return persistenciaOfertas.cargarOfertas(ruta).size();
    }

    public ModeloOferta crearOferta(ModeloOferta oferta) throws Exception {
        validarOferta(oferta);
        PersistenciaOfertas.ofertas.add(oferta);
        persistenciaOfertas.actualizar();

        return oferta;
    }

    private void validarOferta(ModeloOferta oferta) throws Exception {
        LogicaSubastas logicaSubastas = new LogicaSubastas();
        ModeloUsuario usuario = logicaUsuarios.consultarUno(oferta.getEmailOfertante());
        ModeloSubasta subasta = logicaSubastas.consultarUno(oferta.getIdSubasta().toString());
        ModeloPiezas pieza = logicaPiezas.consultarUno(subasta.getTituloPieza());
        ModeloOferta ofertaMayor = this.consultarOfertaMayor(oferta.getIdSubasta());

        // Boolean condicion = usuario.getAutorizado()
        //         && pieza.getEstado().equals(ModeloEstado.EN_SUBASTA)
        //         && subasta.getEstado().equals(ModeloEstadoSubasta.ABIERTA)
        //         && usuario.getLimitePago() >= oferta.getMonto()
        //         && oferta.getMonto() >= subasta.getMontoInicial()
        //         && (ofertaMayor == null || ofertaMayor.getMonto() < oferta.getMonto());
        // if (!condicion) {
        //     throw new Exception("No se puede crear la oferta");
        // }
        if (!usuario.getAutorizado()) {
            throw new Exception("No se puede crear la oferta: Usuario no autorizado");
        }

        if (!pieza.getEstado().equals(ModeloEstado.EN_SUBASTA)) {
            throw new Exception("No se puede crear la oferta: La pieza no está en subasta");
        }

        if (!subasta.getEstado().equals(ModeloEstadoSubasta.ABIERTA)) {
            throw new Exception("No se puede crear la oferta: La subasta no está abierta");
        }

        if (usuario.getLimitePago() < oferta.getMonto()) {
            throw new Exception("No se puede crear la oferta: El monto excede el límite de pago del usuario");
        }

        if (oferta.getMonto() < subasta.getMontoInicial()) {
            throw new Exception("No se puede crear la oferta: El monto es menor al monto inicial de la subasta");
        }

        if (ofertaMayor != null && ofertaMayor.getMonto() >= oferta.getMonto()) {
            throw new Exception("No se puede crear la oferta: El monto es menor o igual a la oferta mayor existente");
        }
    }

    public ModeloOferta consultarOfertaMayor(Integer idSubasta) {
        Integer max = 0;
        ModeloOferta ofertaMayor = null;
        for (ModeloOferta oferta : PersistenciaOfertas.ofertas) {
            if (oferta.getIdSubasta().equals(idSubasta) && oferta.getMonto() > max) {
                max = oferta.getMonto().intValue();
                ofertaMayor = oferta;
            }
        }
        return ofertaMayor;
    }

}
