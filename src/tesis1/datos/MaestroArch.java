package tesis1.datos;
import java.io.*;
/**
 * Title:        Asignacion de horarios
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      ITSON
 * @author Adolfo Espinoza
 * @version 1.0
 */

public class MaestroArch {

  private char  tipo;
  private int   id;
  private String  nom, apell;
  public HorarioSemana horario= new HorarioSemana();

  public MaestroArch() {
  }

  public MaestroArch(char t, int num, String nom, String apell) {
    setNombre(nom,apell);
    setID(t,num);
  }


  public void guardarMaestro(DataOutput archSalida){
    try{
      archSalida.writeChar((int)this.tipo);
      archSalida.writeInt(this.id);
      archSalida.writeBytes(this.nom);
      archSalida.writeByte(0);
      archSalida.writeBytes(this.apell);
      archSalida.writeByte(0);
      horario.guardarHorario(archSalida);
    }
    catch(Exception e) {
      System.out.print("\n error de archivo salida MaestroArch\n");
      e.printStackTrace();
    }
  }

  /**
   * Escribe en un objeto de la clase Maestro lo leido del archivo.
   */
  public void cargarMaestro(DataInput arch){

    try{
      this.tipo=arch.readChar();
      this.id = arch.readInt();
      this.nom = readString(arch);
      this.apell = readString(arch);
      this.horario.cargarHorario(arch);
    }
    catch(Exception e) {
      System.out.print("\n error de lectura en archivo Maestro\n");
      e.printStackTrace();
    }
  }

 private String readString(DataInput arch){
    char[] bufCadena =  new char[20];
    int apChar=0;
    try{
      do{
        bufCadena[apChar++] = (char)arch.readByte();
      }while(bufCadena[apChar-1]!=0);
    }
    catch(Exception e) {
      System.out.print("\n error de archivo entrada String\n");
      e.printStackTrace();
    }
    return new String(bufCadena,0,apChar-1);
  }

  public void setNombre(String nom, String apell){
    this.nom= nom;
    this.apell=apell;
  }

  public void setID(char t, int num){
    tipo = t;
    id=num;
  }

  public String getID(){
    return String.valueOf(this.tipo) + Integer.toString(this.id);
  }

  public String getNombre(){
    return this.apell+"  "+this.nom;
  }

  public String getNomPila(){
    return this.nom;
  }

  public String getApell(){
    return this.apell;
  }

  public HorarioSemana getHorario(){
    return (HorarioSemana)this.horario.clone();
  }


  public void printMaestro(String men){
    System.out.println("printMaestro: "+men+" "+tipo+id+"   "+nom+" "+apell);
  }

  public Object clone(){
    String iD = this.getID();
    MaestroArch nuevoMaestro=new MaestroArch(iD.charAt(0),
                                    new Integer(iD.substring(1,iD.length())).intValue(),
                                     this.getNomPila(),this.getApell());
    nuevoMaestro.horario.setHorario((HorarioSemana)this.horario.clone());
    return nuevoMaestro;
  }

  public void copiarMaestro(MaestroArch original){
    String iD = original.getID();
    this.setID(iD.charAt(0), (new Integer(iD.substring(1,iD.length()))).intValue());

    this.setNombre(original.getNomPila(),original.getApell());
    this.horario.setHorario((HorarioSemana)original.horario.clone());
  }

  public void copiarClavNom(MaestroArch original){
    String iD = original.getID();
    this.setID(iD.charAt(0), (new Integer(iD.substring(1,iD.length()))).intValue());
  System.out.println("ID="+iD+" tipo="+iD.charAt(0)+" longitud="+iD.length()+"  ID(int)="+(new Integer(iD.substring(1,iD.length()))).intValue());
    this.setNombre(original.getNomPila(),original.getApell());
  }

  /**
   * Verifica que maest:   El primer caracter sea "P" o "A", los ultimos 4
   * caracteres sean numero.
   */
  public boolean isMaestroOk(){

    String iD = this.getID();

    int longitud = iD.length();
    char tipo =Character.toTitleCase(iD.charAt(0));
    String numStr= iD.substring(1,longitud);
    int num;

    if(longitud<=3){

      return false;
    }
    if((tipo!='A')&&(tipo!='P')){
      return false;
    }
    try{
      num=(Integer.parseInt(numStr));
      if ((num<0)||(num>9999)) return false;
    }
    catch(NumberFormatException e){
      return false;
    }

    this.setID(tipo,num);
    return  true;
  }


}