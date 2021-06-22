/*Version filtrada 20/06/2021*/

package tesis1;
import tesis1.datos.*;
import tesis1.genetic.*;
import java.util.Random;
public class Genetic {
private Random genRandPP = new Random(4);

 // Construct the application
  public Genetic() {


    try{

      genetico1();
     

    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**Main method*/
  public static void main(String[] args) {
    Genetic ag = new Genetic();

  }
/*
 private int costo(Catalogo cat){
    int empAulas =  cat.aulas.getEmpalmes(); //medias horas empalmadas
    int empMaestro = cat.maestros.getEmpalmes();//medias horas empalmadas
    int empGrpsSC = cat.semCarrs.getBloqueads(); //grupos que no estan en una
                                      //trayectorioa de horario v�lido
    int turnos = cat.semCarrs.costoTurnos();
    int hrsContinuas = cat.maestros.getHrsContinuas();

    return empAulas + empMaestro + empGrpsSC + hrsContinuas + turnos;
  }
*/

/*
  private void imprimeCosto(Catalogo cat){
    int empAulas =  cat.aulas.getEmpalmes(); //medias horas empalmadas
    int empMaestro = cat.maestros.getEmpalmes();//medias horas empalmadas
    int empGrpsSC = cat.semCarrs.getBloqueads(); //grupos que no estan en una
                                      //trayectorioa de horario v�lido
    int hrsContinuas = cat.maestros.getHrsContinuas();
    int turnos = cat.semCarrs.costoTurnos();


    System.out.print("*** COSTO =  ");System.out.println(empAulas + empMaestro + empGrpsSC + hrsContinuas + turnos);
    System.out.print("empAulas=");System.out.print(empAulas);
    System.out.print("  empMaest=");System.out.print(empMaestro);
    System.out.print("  empSC=");System.out.print(empGrpsSC);
    System.out.print("  continuas=");System.out.print(hrsContinuas);
    System.out.print("  turnos=");System.out.println(turnos);
  }

*/


/***************************************************************************
 * Rutinas para pruebas
 */
  private void testImprimeSlotsTime(VectorSlotTime horas){
  //Imprime todos los slotTime`s
    int a, b;
    int cpa = horas.size();
    for(a=0;a<cpa-1;a++)for(b=a+1;b<cpa;b++){
      System.out.print(((SlotTime)horas.get(a)).getOffsetStringHora());
      System.out.print(((SlotTime)horas.get(a)).getDescripcion()+"   ");
      System.out.print(((SlotTime)horas.get(b)).getOffsetStringHora());
      System.out.println(((SlotTime)horas.get(b)).getDescripcion());

      if( ((SlotTime)horas.get(a)).
                              isEmpalme( (SlotTime)horas.get(b) )
      ){System.out.println("Empalme");}       //then "empalme"
      else{System.out.println("No Empalme");}   //else "no empalme"

      //imprime matrices;
      testImprimeMatrizSlotTime(((SlotTime)horas.get(a)));
      testImprimeMatrizSlotTime(((SlotTime)horas.get(b)));
    }
  }

  private void testImprimeMatrizSlotTime(SlotTime st){
    int [][] ar1= st.getMascaraArray();
    for(int r=0; r<6; r ++){
      for(int c=0; c<5;c++){
        System.out.print(ar1[r][c]);
      }
      System.out.println();
    }System.out.println();
  }

/********************************************************************/

/************************************************************************/



  private void genetico1(){
    int POBLMIN = 400;
    int POBLMAX = 800;
    int ELITE = (int)(POBLMAX*0.1);
    double FACTMUT = 0.05;
    int indxElem1, indxElem2, pivote1, pivote2;
    Individuo elem1, elem2, nuevo1, nuevo2;
    boolean finProceso=false;
    int camada=0;

    //Lee datos de entrada
    Catalogo cat1 = new Catalogo("tabGrupos.txt", "tabMaes.dat",
                                    "tabAulas.dat", "tipoHors.txt",
                                    "tabSemCarr.txt", "tabMaterias.txt");

    //Genera poblaci�n inicial
    Poblacion pob = new Poblacion();
    Individuo indAux;
    for(int i=0; i<POBLMIN; i++){
      cat1.borraAsignados();
      cat1.asignaHorRandom();

/*
indAux = cat1.getIndividuo();
indAux.evalua(cat1);
*/
cat1.evaluaCosto();
indAux = cat1.getIndividuo2();

      pob.add(indAux);
    }


    do{
      //Mutacion
      //System.out.print("Mutaci�n,  ");

      int gpoIndx, opcIndx, selec;
      Grupo gpoAux;
      Individuo indiviNuevo;
      int tamahoInic = pob.size();
      for(int i=0; i<tamahoInic; i++){
        if(genRandPP.nextInt(10000)<=FACTMUT*10000){
          indiviNuevo=(Individuo)((Individuo)pob.get(i)).clone();
          gpoIndx=genRandPP.nextInt(indiviNuevo.getGenes().length);
          gpoAux=(Grupo)cat1.grupos.get(gpoIndx);
          selec=gpoAux.getOpcs()[genRandPP.nextInt(gpoAux.getOpcs().length)];
          indiviNuevo.setGene(gpoIndx,selec);

/*
indiviNuevo.evalua(cat1);
*/
cat1.borraAsignados();
cat1.asignaIndividuo(indiviNuevo);
cat1.evaluaCosto();
indiviNuevo=cat1.getIndividuo2();

          pob.add(indiviNuevo);
        }
      }


      //Cruzamiento
      //System.out.print("Cruza,  ");
      int tamaho=cat1.grupos.size();
      do{
        indxElem1=genRandPP.nextInt(pob.size());
        do{indxElem2=genRandPP.nextInt(pob.size());}while(indxElem1==indxElem2);
        elem1=((Individuo)pob.get(indxElem1));
        elem2=((Individuo)pob.get(indxElem2));
        nuevo1=(Individuo)elem1.clone();
        nuevo2=(Individuo)elem2.clone();

        pivote1=genRandPP.nextInt(tamaho);
        do{ pivote2=genRandPP.nextInt(tamaho); }while(pivote1==pivote2);
        int inic=(pivote1<pivote2)? pivote1: pivote2;
        int fin=(pivote1>pivote2)? pivote1: pivote2;

        for(int i=inic; i<=fin; i++){
          nuevo1.setGene(i,elem2.getGenes()[i]);
          nuevo2.setGene(i,elem1.getGenes()[i]);
        }

/*
nuevo1.evalua(cat1);
*/
cat1.borraAsignados();
cat1.asignaIndividuo(nuevo1);
cat1.evaluaCosto();
nuevo1=cat1.getIndividuo2();

/*
nuevo2.evalua(cat1);
*/
cat1.borraAsignados();
cat1.asignaIndividuo(nuevo2);
cat1.evaluaCosto();
nuevo2=cat1.getIndividuo2();

        pob.add(nuevo1);
        pob.add(nuevo2);

      }while(pob.size()<POBLMAX);


      //Seleccion
      //System.out.println("Selecciona,  ");

      pob.ordenaMenMayor();

      int costo1, costo2;
      do{
        //Ruleta

        //Torneo
        indxElem1 = genRandPP.nextInt(pob.size()-ELITE) + ELITE;
        indxElem2 = genRandPP.nextInt(pob.size()-ELITE) + ELITE;

/*
costo1=((Individuo)pob.elementAt(indxElem1)).getCostoTotal();
costo2=((Individuo)pob.elementAt(indxElem2)).getCostoTotal();
*/
costo1=((Individuo)pob.get(indxElem1)).getCosto().getCostoTotal() ;
costo2=((Individuo)pob.get(indxElem2)).getCosto().getCostoTotal() ;


        if( costo1 > costo2 ){
              pob.remove(indxElem1);
        }else if(costo1 < costo2){
              pob.remove(indxElem2);
        }
        if( costo1 == costo2 ){
          if(genRandPP.nextInt(2)==0) pob.remove(indxElem1);
          else  pob.remove(indxElem2);
        }
      }while(pob.size()>POBLMIN);

      int minimo=100000;
      int maximo=0;
      int promedio=0;
      int indxMinimo=0;
      int costo=0;
      for(int i=0; i<pob.size(); i++){

/*
costo=((Individuo)pob.elementAt(i)).getCostoTotal();
*/
costo=((Individuo)pob.get(i)).getCosto().getCostoTotal();


if(costo<minimo){
  minimo=costo;
  indxMinimo=i;
}
//minimo= (costo<minimo)? costo : minimo;


        maximo= (costo>maximo)? costo : maximo;
        promedio += costo;
      }
      promedio=promedio/pob.size();
      System.out.print(camada++ +",   "+minimo+", "+promedio+", "+maximo+",   ");

((Individuo)pob.get(indxMinimo)).getCosto().imprimeCosto();

      if(camada%10==0){
        System.out.print(",  ini= "+pob.size());
        pob.eliminaRepetidos();
        System.out.println(",  fin= "+pob.size());
      }//else  System.out.println();

      if(minimo<=42)finProceso=true;
    }while(!finProceso);


    for(int i=0; i<100; i++){
      indAux=(Individuo)pob.get(i);
      System.out.print(i+",   ");
      for(int j=0; j<indAux.getGenes().length; j++){
        System.out.print(indAux.getGenes()[j] + ", ");
      }
      System.out.println();
    }

  }// end geneticol()



/************************************************************************/



}