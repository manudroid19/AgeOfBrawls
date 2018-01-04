/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ageofbrawls.plataforma;

import ageofbrawls.z.excepciones.AccionRestringida.ExcepcionAccionRestringidaPersonaje;
import ageofbrawls.z.excepciones.Argumentos.ExcepcionArgumentosInternos;
import ageofbrawls.z.excepciones.Recursos.ExcepcionCorrespondenciaRecursos;
import ageofbrawls.z.excepciones.noExiste.ExcepcionNoExisteArchivo;

/**
 *
 * @author mprad
 */
public class CargadorNuevo implements CargadorJuego{

    @Override
    public Juego cargarJuego() throws ExcepcionNoExisteArchivo, ExcepcionArgumentosInternos, ExcepcionCorrespondenciaRecursos, ExcepcionAccionRestringidaPersonaje {
        return new Juego();
    }
    
}
