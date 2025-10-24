
public class InterfaceForAssemblyLine {
    public static void main(String[] args) {
        System.out.println("Проверка работы сборочной линии.");
        System.out.println("-------------------------------------------------");

        //Заготовка
        Laptop laptop = new Laptop();

        AssemblyLine lineForAssebly = new AssemblyLine(new Corpus(), new MotherBoard(), new Display());
        lineForAssebly.assembleProduct(laptop);

    }
}

interface IAssemblyLine {
    IProduct assembleProduct(IProduct iproduct);
}

//Сборочная линия
class AssemblyLine implements IAssemblyLine{
    IProductPart corpus;
    IProductPart motherboard;
    IProductPart display;

    AssemblyLine(IProductPart corpus, IProductPart motherboard, IProductPart display) {
        this.corpus = corpus;
        this.motherboard = motherboard;
        this.display = display;
    }

    @Override
    public IProduct assembleProduct(IProduct iproduct) {
        iproduct.installFirstPart(this.corpus);
        System.out.println("-------------------------------------------------");
        iproduct.installSecondPart(this.motherboard);
        System.out.println("-------------------------------------------------");
        iproduct.installThirdPart(this.display);
        System.out.println("-------------------------------------------------");
        Laptop laptop = (Laptop) iproduct;
        System.out.println("Сборка ноутбука завершена.");
        return laptop;
    }




}

interface IProduct {
    void installFirstPart(IProductPart iproductpart);
    void installSecondPart(IProductPart iproductpart);
    void installThirdPart(IProductPart iproductpart);
}

//Ноутбук
class Laptop implements IProduct {
    private IProductPart corpus;
    private IProductPart motherboard;
    private IProductPart display;

    //Конструктор по умолчанию
    Laptop() {
        this.corpus = new Corpus();
        this.motherboard = new MotherBoard();
        this.display = new Display();
    }

    //Конструктор со всеми параметрами
    Laptop(Corpus corpus, MotherBoard motherboard, Display display) {
        this.corpus = corpus;
        this.motherboard = motherboard;
        this.display = display;
    }

    @Override
    public void installFirstPart(IProductPart iproductpart) {
        System.out.println("Подготовка корпуса к сборке");
        this.corpus = iproductpart.buildProductPart();
        System.out.println("Корпус помещён на сборочную линию.");
    }

    @Override
    public void installSecondPart(IProductPart iproductpart) {
        System.out.println("Подготовка материнской платы к сборке");
        this.motherboard = iproductpart.buildProductPart();
        System.out.println("Материнская плата помещёна на сборочную линию.");
        System.out.println("Материнская плата монтирована в корпус.");
    }

    @Override
    public void installThirdPart(IProductPart iproductpart) {
        System.out.println("Подготовка экрана к сборке");
        this.display = iproductpart.buildProductPart();
        System.out.println("Экран помещён на сборочную линию.");
        System.out.println("Экран монтирован в корпус.");
    }

    public IProductPart getCorpus() {
        return this.corpus;
    }

    public IProductPart getMotherBoard() {
        return this.motherboard;
    }

    public IProductPart getDisplay() {
        return this.display;
    }
}

interface ILineStep {
    IProductPart buildProductPart();
}

interface IProductPart extends ILineStep{
    IProductPart buildProductPart();
}

abstract class LaptopPart implements IProductPart{
    public abstract IProductPart buildProductPart();
}

//Корпус корпуса
class Corpus extends LaptopPart {
    private String material;
    private int lengthMM;
    private int widthMM;


    //Конструктор по умолчанию
    public Corpus() {
        super();
        this.material = "Пластик";
        this.lengthMM = 300;
        this.widthMM = 250;
    }

    // Конструктор со всеми параметрами
    public Corpus(String material, int lengthmm, int widthmm) {
        super();
        this.material = material;
        this.lengthMM = lengthmm;
        this.widthMM = widthmm;
    }

    @Override
    public IProductPart buildProductPart() {
        System.out.println("Подготовка формы для изготовления корпуса.");
        System.out.println("Начало изготовления корпуса.");
        Corpus corpus = new Corpus();
        System.out.println("Изготовление корпуса завершено.");
        return corpus;
        
    }

    //Геттеры и сеттеры
    public String getMaterial() {
        return this.material;
    }

    public int getLengthMM() {
        return this.lengthMM;
    }

    public int getWidthMM() {
        return this.widthMM;
    }

}

//Материнская плата
class MotherBoard extends LaptopPart {
    private String Socket;
    private String nameCPU;
    private int cntSlotsRAM;

    //Конструктор по умолчанию
    MotherBoard() {
        super();
        this.Socket = "LGA 1700";
        this.nameCPU = "intel i5";
        this.cntSlotsRAM = 1;
    }

    //Конструктор со всеми параметрами
    MotherBoard(String format, String socket, String namecpu, int cntslotsram) {
        super();
        this.Socket = socket;
        this.nameCPU = namecpu;
        this.cntSlotsRAM = cntslotsram;
    }

    @Override
    public IProductPart buildProductPart() {
        System.out.println("Подготовка платы для монтажа элементов.");
        System.out.println("Монтаж элементов.");
        System.out.println("Установка чипсета.");
        System.out.println("Установка процессора.");
        MotherBoard motherboard = new MotherBoard();
        System.out.println("Производство материнской платы завершено.");
        return motherboard;
        
    }

    //Геттеры и сеттеры
    public String getSocket() {
        return this.Socket;
    }

    public String getNameCPU() {
        return this.nameCPU;
    }

    public void setNameCPU(String namecpu) {
        this.nameCPU = namecpu;
    }

    public int getCntSlotsRAM() {
        return this.cntSlotsRAM;
    }
}

//Экран
class Display extends LaptopPart {
    private int Brightness;
    private float Diagonal;
    private String typeMatrix;

    // Конструктор по умолчанию
    public Display() {
        super();
        this.Brightness = 250;
        this.Diagonal = 14.0f;
        this.typeMatrix = "TN";
    }

    // Конструктор со всеми параметрами
    public Display(int brightness, float diagonal, String typematrix) {
        super();
        this.Brightness = brightness;
        this.Diagonal = diagonal;
        this.typeMatrix = typematrix;
    }

    @Override
    public IProductPart buildProductPart() {
        System.out.println("Подготовка основы для изготовления экрана.");
        System.out.println("Установка матрицы в экран.");
        Display display = new Display();
        System.out.println("Изготовление экрана завершено.");
        return display;
    }

    //Геттеры и сеттеры
    int getBrightness() {
        return this.Brightness;
    }

    float getDiagonal() {
        return this.Diagonal;
    }

    String getTypeMatrix() {
        return this.typeMatrix;
    }

    void setTypeMatrix(String newValue) {
        this.typeMatrix = newValue;
    }
    
}