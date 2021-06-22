package tesis1.datos;

/**
 * Title:        Asignacion de horarios
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      ITSON
 * @author Adolfo Espinoza
 * @version 1.0
 */

public class Aula extends Recurso implements Cloneable{
  private String clave;

  public Aula() {
  }

  public Object clone(){
    Aula aux = new Aula();
    aux.setClave( new String(clave) );
    aux.setFactib( (HorarioSemana)this.getFactib().clone() );
    aux.setUso( (HorarioSemana)this.getUso().clone() );
    aux.empalmes=this.empalmes;

    return aux;
  }

  public void setClave(String c){
    clave=c;
  }
  public String getClave(){
    return new String(clave);
  }


  public void copiaDesde(AulaArch aulaEnt){
    int r, c;
    HorarioSemana hs = (HorarioSemana)aulaEnt.horario.clone();

    for(r=0; r<28; r++)for(c=0; c<5; c++){
      this.setFactib( r, c, (short)hs.get(r,c) );
    }
    this.clave=new String(aulaEnt.getClave());
  }

}