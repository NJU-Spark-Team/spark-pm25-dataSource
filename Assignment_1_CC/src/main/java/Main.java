import CSV.CSVReader;

public class Main {
    public static void main(String[] args) {
        boolean serverFlag = false;
        int port = -1;
        String path = "/";

        if (args.length == 0) {
            warningAndExit();
        } else {
            if (args[0].equals("server"))
                serverFlag = true;
            else if (args[0].equals("csv"))
                serverFlag = false;
            else {
                warningAndExit();
            }
        }

        if (serverFlag) {
            for (int i = 1; i < args.length - 1; i++)
                if (args[i].equals("--port")) {
                    port = Integer.parseInt(args[i + 1]);
                    break;
                }
            if (port == -1)
                warningAndExit();
            else {
                LogicServer server = new LogicServer();
                server.startAction(port);
            }
        } else {
            for (int i = 1; i < args.length - 1; i++)
                if (args[i].equals("--path")) {
                    path = args[i + 1];
                    break;
                }
            CSVReader.readAndClear(path);
        }
    }

    private static void warningAndExit() {
        System.err.println("Usage: datasource [server|csv] --port <socket port> --path <csv folder path>");
        System.exit(1);
    }
}
