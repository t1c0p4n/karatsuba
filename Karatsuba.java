import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Karatsuba {
   
    // Método para cálculo da multiplicação via Karatsuba.
    public static BigInteger karatsuba(BigInteger n1, BigInteger n2) {
        int pos = Math.max(n1.bitLength(), n2.bitLength());
        //Para n < 512, é mais eficiente a multiplicação tradicional.
        if (pos <= 512) {
            return n1.multiply(n2);
        } //Para n > 512, executamos o método Karatsuba
        pos = pos / 2;
        
        BigInteger w = n1.shiftRight(pos);
        BigInteger x = n1.subtract(w.shiftLeft(pos));
        BigInteger y = n2.shiftRight(pos);
        BigInteger z = n2.subtract(y.shiftLeft(pos));
        // Resultados parciais
        BigInteger p = karatsuba(w, y); //p=w*y
        BigInteger q = karatsuba(x, z); //q=x*z
        BigInteger r = karatsuba(x.add(w), z.add(y)); //r=(x+w)*(z+y)
        BigInteger z1 = r.subtract(p).subtract(q); //r-p-q
        // Juntamos os resultados parciais para obter o resultado global.
        return p.shiftLeft(2 * pos).add(z1.shiftLeft(pos)).add(q);
      }

    /* Método de multiplicação tradicional */
    public static BigInteger tradicional(BigInteger n1, BigInteger n2){
        return n1.multiply(n2);
    }

    public static void main(String[] args) throws Exception {
        String n1 = "1"; //Iniciando a variável do primeiro número
        String n2 = "1"; //Iniciando a variável do segundo número
        String path = System.getProperty("user.dir"); //Pegando o diretório em que o usuário está executando o código.
        
        /* Definindo arquivo de entrada */
        File fileInput = new File (path + "/input.txt");
        System.out.println("ATENÇÃO: Para execução desse código é necessário apontar corretamente a variável 'fileInput', que atualmente está recebendo o seguinte arquivo: " + fileInput);
        
        try {
            FileReader input = new FileReader(fileInput);
            BufferedReader lerArq = new BufferedReader(input);

            String linha = lerArq.readLine(); // Lendo a primeira linha
            n1 = linha; //Passando a variável "linha" para a nova variável "n1"

            linha = lerArq.readLine();
            n2 = linha; //Passando a variável "linha" para a nova variável "n2"
            input.close(); // Fechando arquivo de leitura
        
        } catch (IOException e) {
            System.out.printf("Erro na abertura do arquivo: %s. \n", e.getMessage());
        }

        /* Executando o método tradicional e medindo tempo de execução */
        long tempoInicio2 = System.currentTimeMillis();
        BigInteger resultadoTradicional = tradicional(new BigInteger(n1), new BigInteger(n2));
        long tempoTotalT = (System.currentTimeMillis()-tempoInicio2);
        //System.out.println("Resultado Multiplicação Tradicional: " + resultadoTradicional);
        System.out.println("Tempo de execução Tradicional: " + tempoTotalT);

        /* Executando o método Karatsuba e medindo tempo de execução */
        long tempoInicio = System.currentTimeMillis();
        BigInteger resultadoKaratsuba = karatsuba(new BigInteger(n1), new BigInteger(n2));
        long tempoTotalK = (System.currentTimeMillis()-tempoInicio);
        //System.out.println("Resultado Multiplicação Karatsuba: " + resultadoKaratsuba);
        System.out.println("Tempo de execução Karatsuba: " + tempoTotalK);

        /* Definindo arquivo de saída */
        File fileOutput = new File (path + "/output.txt");
        try {
            FileWriter output = new FileWriter(fileOutput);
            BufferedWriter escreveArq = new BufferedWriter(output);
            /* Escrevendo o resutado da multiplicação Karatsuba no arquivo output.txt */
            escreveArq.write("O resultado da multiplicação Karatsuba é: " + resultadoKaratsuba); //System.out.println("O resultado da multiplicação Karatsuba é: " +  karatsuba(new BigInteger(n1), new BigInteger(n2)));
            escreveArq.write("\n Tempo de execução [Karatsuba]: " + tempoTotalK); //System.out.println("Tempo de execução [Karatsuba]:" + (System.currentTimeMillis()-tempoInicio));
            escreveArq.newLine(); //System.out.println();
            /* Escrevendo o resutado da multiplicação tradicional no arquivo output.txt */                        
            escreveArq.write("O resultado da multiplicação tradicional é: " + resultadoTradicional); //System.out.println("O resultado da multiplicação tradicional é: " + tradicional(new BigInteger(n1), new BigInteger(n2)));
            escreveArq.write("\n Tempo de execução [Tradicional]: " + tempoTotalT); //System.out.println("Tempo de execução [Tradicional]:" + (System.currentTimeMillis()-tempoInicio2));
            escreveArq.newLine();
            escreveArq.flush();

            /*Fechando arquivo output.txt*/
            output.flush();
            output.close();
        } catch (IOException e) {
            System.out.printf("Erro na abertura do arquivo: %s. \n", e.getMessage());
        }
        
    }
     
}