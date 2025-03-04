package grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: sql.proto")
public final class AuthServiceGrpc {

  private AuthServiceGrpc() {}

  public static final String SERVICE_NAME = "grpc.AuthService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.Sql.ValidateTokenRequest,
      grpc.Sql.ValidateTokenResponse> getValidateTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ValidateToken",
      requestType = grpc.Sql.ValidateTokenRequest.class,
      responseType = grpc.Sql.ValidateTokenResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.Sql.ValidateTokenRequest,
      grpc.Sql.ValidateTokenResponse> getValidateTokenMethod() {
    io.grpc.MethodDescriptor<grpc.Sql.ValidateTokenRequest, grpc.Sql.ValidateTokenResponse> getValidateTokenMethod;
    if ((getValidateTokenMethod = AuthServiceGrpc.getValidateTokenMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getValidateTokenMethod = AuthServiceGrpc.getValidateTokenMethod) == null) {
          AuthServiceGrpc.getValidateTokenMethod = getValidateTokenMethod = 
              io.grpc.MethodDescriptor.<grpc.Sql.ValidateTokenRequest, grpc.Sql.ValidateTokenResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "grpc.AuthService", "ValidateToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.Sql.ValidateTokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.Sql.ValidateTokenResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("ValidateToken"))
                  .build();
          }
        }
     }
     return getValidateTokenMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AuthServiceStub newStub(io.grpc.Channel channel) {
    return new AuthServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AuthServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new AuthServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AuthServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new AuthServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class AuthServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void validateToken(grpc.Sql.ValidateTokenRequest request,
        io.grpc.stub.StreamObserver<grpc.Sql.ValidateTokenResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getValidateTokenMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getValidateTokenMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.Sql.ValidateTokenRequest,
                grpc.Sql.ValidateTokenResponse>(
                  this, METHODID_VALIDATE_TOKEN)))
          .build();
    }
  }

  /**
   */
  public static final class AuthServiceStub extends io.grpc.stub.AbstractStub<AuthServiceStub> {
    private AuthServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AuthServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AuthServiceStub(channel, callOptions);
    }

    /**
     */
    public void validateToken(grpc.Sql.ValidateTokenRequest request,
        io.grpc.stub.StreamObserver<grpc.Sql.ValidateTokenResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getValidateTokenMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AuthServiceBlockingStub extends io.grpc.stub.AbstractStub<AuthServiceBlockingStub> {
    private AuthServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AuthServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AuthServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public grpc.Sql.ValidateTokenResponse validateToken(grpc.Sql.ValidateTokenRequest request) {
      return blockingUnaryCall(
          getChannel(), getValidateTokenMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AuthServiceFutureStub extends io.grpc.stub.AbstractStub<AuthServiceFutureStub> {
    private AuthServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AuthServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AuthServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.Sql.ValidateTokenResponse> validateToken(
        grpc.Sql.ValidateTokenRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getValidateTokenMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_VALIDATE_TOKEN = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AuthServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AuthServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_VALIDATE_TOKEN:
          serviceImpl.validateToken((grpc.Sql.ValidateTokenRequest) request,
              (io.grpc.stub.StreamObserver<grpc.Sql.ValidateTokenResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class AuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AuthServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.Sql.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AuthService");
    }
  }

  private static final class AuthServiceFileDescriptorSupplier
      extends AuthServiceBaseDescriptorSupplier {
    AuthServiceFileDescriptorSupplier() {}
  }

  private static final class AuthServiceMethodDescriptorSupplier
      extends AuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AuthServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AuthServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AuthServiceFileDescriptorSupplier())
              .addMethod(getValidateTokenMethod())
              .build();
        }
      }
    }
    return result;
  }
}
