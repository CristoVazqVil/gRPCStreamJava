package grpcholamundostream.cliente;

import com.proto.saludo.SaludoServiceGrpc;
import com.proto.saludo.Holamundo.SaludoRequest;
import com.proto.saludo.Holamundo.SaludoResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

//Cristopher Vázquez Villa

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 8080;

        ManagedChannel ch = ManagedChannelBuilder
            .forAddress(host, puerto)
            .usePlaintext()
            .build();

        //saludarUnario(ch);
        //saludarStream(ch);
        archivotote(ch);

        System.out.println("Apagando...");
        ch.shutdown();
    }

    public static void saludarUnario(ManagedChannel ch) {
        SaludoServiceGrpc.SaludoServiceBlockingStub stub = SaludoServiceGrpc.newBlockingStub(ch);
        SaludoRequest peticion = SaludoRequest.newBuilder().setNombre("Cristopher Vazquez").build();
        SaludoResponse respuesta = stub.saludo(peticion);

        System.out.println("Respuesta RPC: " + respuesta.getResultado());
    }

    public static void saludarStream(ManagedChannel ch) {
        SaludoServiceGrpc.SaludoServiceBlockingStub stub = SaludoServiceGrpc.newBlockingStub(ch);
        SaludoRequest peticion = SaludoRequest.newBuilder().setNombre("Cristopher Vazquez").build();

        stub.saludoStream(peticion).forEachRemaining(respuesta -> {
            System.err.println("Respuesta RPC: " + respuesta.getResultado());
        });
    }

    public static void archivotote(ManagedChannel ch) {
        SaludoServiceGrpc.SaludoServiceBlockingStub stub = SaludoServiceGrpc.newBlockingStub(ch);
        SaludoRequest peticion = SaludoRequest.newBuilder().setNombre("archivote.csv").build();

        stub.archivote(peticion).forEachRemaining(respuesta -> {
            System.out.println(respuesta.getResultado());
        });
    }
}
