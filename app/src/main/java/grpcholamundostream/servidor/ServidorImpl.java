package grpcholamundostream.servidor;

import java.util.Scanner;
import com.proto.saludo.Holamundo.SaludoRequest;
import com.proto.saludo.Holamundo.SaludoResponse;
import com.proto.saludo.SaludoServiceGrpc;
import io.grpc.stub.StreamObserver;

//Cristopher Vázquez Villa

public class ServidorImpl extends SaludoServiceGrpc.SaludoServiceImplBase{
    
    @Override
    public void saludo(SaludoRequest request, StreamObserver<SaludoResponse> responseObserver) {
        SaludoResponse respuesta = SaludoResponse.newBuilder().setResultado("Hola " + request.getNombre()).build();
        responseObserver.onNext(respuesta);
        responseObserver.onCompleted();
    }

    @Override
    public void saludoStream(SaludoRequest request, StreamObserver<SaludoResponse> responseObserver) {
        for (int i = 0; i <= 10;i++) {
            SaludoResponse respuesta = SaludoResponse.newBuilder()
                .setResultado("Hola " + request.getNombre() + " por " + i + " vez.").build();
            
            responseObserver.onNext(respuesta);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void archivote(SaludoRequest request, StreamObserver<SaludoResponse> responseObserver) {
        try (Scanner scanner = new Scanner(getClass().getResourceAsStream("/archivote.csv"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                SaludoResponse respuesta = SaludoResponse.newBuilder().setResultado(line).build();
                responseObserver.onNext(respuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }
}
