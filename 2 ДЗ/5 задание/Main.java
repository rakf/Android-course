import java.util.ArrayList;

interface FileSystemNode
{ 
    public FileSystemNode getParent();

    public String getName();

    public String getPath();

}

abstract class AbstractFileSystemNode implements FileSystemNode
{
    protected String name = "";
    protected AbstractFileSystemNode parent;

    AbstractFileSystemNode(String name)
    {
        this.name = name;
    }

    @Override
    public final FileSystemNode getParent()
    {
        return parent;
    }

    @Override
    public String getName()
    {
        return name + "/";
    }

    @Override
    public final String getPath()
    {
        if( parent != null )
            return parent.getPath() + getName();
        return getName();
    }
}

class Folder extends AbstractFileSystemNode
{
    ArrayList< AbstractFileSystemNode > child_elements = new ArrayList<>();

    Folder(String name, AbstractFileSystemNode ... child_elements)
    {
        super(name);
        for( AbstractFileSystemNode el : child_elements )
        {
            el.parent = this;
            this.child_elements.add(el);
        }
    }

    public ArrayList< AbstractFileSystemNode > getChildElements()
    {
        return child_elements;
    }
}

class File extends AbstractFileSystemNode
{
    String extension = "";

    File( String name, String extension )
    {
        super(name);
        this.extension = extension;
    }

    @Override
    public String getName()
    {
        return name + '.' + extension;
    }

    public String getExtension()
    {
        return extension;
    }
}

public class Main
{
    
    public static void main(String[] args) {

        Folder folder = new Folder("Test_folder");
        assert folder.getChildElements().isEmpty();
        assert folder.getParent() == null;
        assert folder.getName().equals("Test_folder/");

        Folder folder_2 = new Folder("Test_folder2",
            new File("Inner_file1", "txt"),
            new File("Inner_file2", "log")
        );

        assert folder_2.child_elements.size() == 2;
        System.out.println( folder_2.getChildElements().get(0).getPath() );
        System.out.println( ((File)folder_2.getChildElements().get(0)).getExtension() );

        folder = new Folder("Test_folder", 
            new Folder("Inner_folder_1"),
            new Folder("Inner_folder_2"),
            new File("Inner_file1", "txt"),
            new File("Inner_file2", "log"),
            folder_2);

        System.out.println( folder.getChildElements().get(0).getPath() );
        Folder inner_folder = (Folder)folder.getChildElements().get(4);
        System.out.println( inner_folder.getPath() );
        System.out.println( inner_folder.getChildElements().get(0).getPath() );

	}
}
