import java.nio.ByteBuffer;

class LoopBackTimeStamp {
    private long sendTimeStamp;
    private long recvTimeStamp;

    LoopBackTimeStamp() {
        this.sendTimeStamp = System.nanoTime();
    }

    long timeLapseInNanoSecond() {
        return recvTimeStamp - sendTimeStamp;
    }

    void setRecvTimeStamp(long time){
        this.recvTimeStamp = time;
    }
    /**
     * Transfer 2 long number to a 16 byte-long byte[], every 8 bytes represent a long number.
     */
    byte[] toByteArray() {

        final int byteOfLong = Long.SIZE / Byte.SIZE;
        byte[] ba = new byte[byteOfLong * 2];
        byte[] t1 = ByteBuffer.allocate(byteOfLong).putLong(sendTimeStamp).array();
        byte[] t2 = ByteBuffer.allocate(byteOfLong).putLong(recvTimeStamp).array();

        System.arraycopy(t1, 0, ba, 0, byteOfLong);

        System.arraycopy(t2, 0, ba, 8, byteOfLong);
        return ba;
    }

    /**
     * Transfer a 16 byte-long byte[] to 2 long numbers, every 8 bytes represent a long number.
     */
    void fromByteArray(byte[] content) {
        int len = content.length;
        final int byteOfLong = Long.SIZE / Byte.SIZE;
        if (len != byteOfLong * 2) {
            System.out.println("Error on content length");
            return;
        }
        ByteBuffer buf1 = ByteBuffer.allocate(byteOfLong).put(content, 0, byteOfLong);
        ByteBuffer buf2 = ByteBuffer.allocate(byteOfLong).put(content, byteOfLong, byteOfLong);
        buf1.rewind();
        buf2.rewind();
        this.sendTimeStamp = buf1.getLong();
        this.recvTimeStamp = buf2.getLong();
    }

    // getter/setter ignored
}