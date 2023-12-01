package git.ridglef.litematica.printer.modules;

import git.ridglef.litematica.printer.impl.ExampleAddonModule;

import javax.print.*;

public class LitematicaPrinter extends ExampleAddonModule {

    public LitematicaPrinter() {
        super("LitematicaPrinter", "Litematica Printer", "Prints litematica");
    }

    @Override
    public void onEnable() {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printer : printServices) {
            try {
                DocFlavor flavor = DocFlavor.STRING.TEXT_PLAIN;
                String printerName = "litematica";
                Doc doc = new SimpleDoc(printerName, flavor, null);
                DocPrintJob printJob = printer.createPrintJob();
                printJob.print(doc, null);
            } catch (PrintException e) {
                e.printStackTrace();
            }
        }
        setState(false);
    }
}
