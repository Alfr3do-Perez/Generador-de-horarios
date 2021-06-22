package tesis1.datos;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class Maestro extends Recurso implements Cloneable{
  private String nombre;
  private String clave;

  public Maestro() {
  }

  public Object clone(){
    Maestro aux = new Maestro();
    aux.setNombre( new String(nombre) );
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
    return clave;
  }

   public void setNombre(String c){
    nombre=c;
  }
  public String getNombre(){
    return nombre;
  }

  public int horsContinuas(int limite){
    int r, c, cont;
    int exceso=0;
    for(c=0; c<this.COLS; c++){
      cont=0;
      for(r=0; r<this.RENGLS; r++){
        if(this.getUso(r,c)==0){ cont=0;
        }else{
          if(++cont > 6)exceso++;
        }
      }
    }
    return exceso;
  }

  /**
   * Copia los datos del maestro desde una instancia de la clase MaestroArch.
   */
  public void copiaDesde(MaestroArch maestroEnt){
    short dato;
    int r, c;
    HorarioSemana hs = (HorarioSemana)maestroEnt.horario.clone();
    for(r=0; r<this.RENGLS; r++)for(c=0; c<this.COLS; c++){
      if(hs.get(r,c)!=0) this.setFactib(r,c,(short)1);
      else this.setFactib(r,c,(short)0);
    }

    this.nombre=new String(maestroEnt.getNombre());
    this.clave=new String(maestroEnt.getID());
  }

}