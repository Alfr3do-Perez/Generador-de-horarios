package tesis1.datos;
import java.util.ArrayList;
import tesis1.io.*;
import tesis1.genetic.*;

/**
 * Title:        Asignaci�n de horarios
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      ITSON
 * @author Adolfo Espinoza
 * @version 1.0
 */

public class VectorGrupos extends ArrayList<Grupo> implements Cloneable{

  public VectorGrupos(String archGrupos, VectorMaestros maestros) {
    leeArchGrupos(archGrupos, maestros);
  }
   public VectorGrupos() {
  }

  public Object clone(){
    VectorGrupos aux = new VectorGrupos();
    int nGrps=this.size();
    for(int i=0; i<nGrps; i++){
      aux.add((Grupo)this.get(i).clone());
    }
    return aux;
  }

  private void leeArchGrupos(String arch, VectorMaestros maestros){
    String params [];
    int numParams;
    ParamFileRead grps = new ParamFileRead(arch);
    boolean eof=false;
    while(null!=(params = grps.readParams()) ){//Mientras no termine el archivo
      numParams = params.length;
      if (numParams==3){
        Grupo buff = new Grupo();
        buff.setClave( params[0] );
        buff.setNum( Integer.parseInt( params[1]) );

        //Coloca el indice de la clave en el atributo "maestro" del
        //objeto "grupo".
        buff.setMaestro( maestros.getIndexClave(params[2]) );

        this.add(buff); // agrega nuevo grupo al vector
      }
    }
  }

/**
 * Coloca el indice del aula en la tabla aulas lo coloca en el atributo
 * aula del grupo.
     * @param aulas
     * @param materias
 */
  public void asignaAulas(VectorAulas aulas, VectorMaterias materias){

    int nGrups = this.size();
    for(int i=0; i<nGrups; i++){
      String mat = ((Grupo)this.get(i)).getClave();
      int indxMat = materias.getIndexClave(mat);
      String aul=((Materia)materias.get(indxMat)).getTipoAula();
      int indxAula = aulas.getIndexClave(aul);
      ((Grupo)this.get(i)).setAula(indxAula);
    }
  }

/**
 * Asigna el vector de horarios factibles para cada grupo. Todos los horarios
 * que se le pueden asignar a ese tipo de materia y que caen dentro del horario
 * factible del maestro.
     * @param horas
     * @param maestros
     * @param materias
 */
  public void asignaOpcsSlotTimes(VectorSlotTime horas, VectorMaestros maestros, VectorMaterias materias){

    int numGrups = this.size();

    for(int g=0; g<numGrups; g++){
      Grupo grup = (Grupo)this.get(g);
      Maestro maest = (Maestro)maestros.get(grup.getMaestro());

      String claveMat=((Grupo)this.get(g)).getClave();
      int indxMat = materias.getIndexClave(claveMat);
      Materia mate = (Materia) materias.get(indxMat);

      grup.setOpcs( horas.getHorsFacts(mate, maest) );
    }
  }

  public Individuo getIndividuo(){
    Individuo aux = new Individuo(this.size());
    for(int i=0; i<this.size(); i++){
      aux.setGene(i,((Grupo)this.get(i)).getHora());
    }
    return aux;
  }

}