package co.id.gpay.wallet.header;

public class RequestContext {

    private static final ThreadLocal<RequestHeader> CONTEXT = new ThreadLocal<>();

    public static void set(RequestHeader header) {
        CONTEXT.set(header);
    }

    public static RequestHeader get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
