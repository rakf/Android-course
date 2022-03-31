interface FileSystemNode {
    fun getParent(): FileSystemNode?
    fun getName(): String?
    fun getPath(): String?
}

abstract class AbstractFileSystemNode(name: String) : FileSystemNode {

    protected var _name: String?
    var parent: AbstractFileSystemNode? = null

    init {
        this._name = name
    }

    final override fun getParent(): FileSystemNode? {
        return parent
    }

    override fun getName(): String? {
        return _name
    }

    final override fun getPath(): String? {
        return if (parent != null) parent!!.getPath() + getName() else getName()
    }
}

class Folder(name: String, vararg child_elements: AbstractFileSystemNode) :
    AbstractFileSystemNode(name) {
    var childElements = ArrayList<AbstractFileSystemNode>()

    init {
        for (el in child_elements) {
            el.parent = this
            childElements.add(el)
        }
    }

    override fun getName(): String? {
        return "$_name/"
    }
}

class File(name: String, extension: String) : AbstractFileSystemNode(name) {
    var extension = ""

    init {
        this.extension = extension
    }

    override fun getName(): String {
        return "${_name}.$extension"
    }
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        var folder = Folder("Test_folder")
        assert(folder.childElements.isEmpty())
        assert(folder.getParent() == null)
        assert(folder.getName() == "Test_folder/")
        val folder_2 = Folder(
            "Test_folder2",
            File("Inner_file1", "txt"),
            File("Inner_file2", "log")
        )
        assert(folder_2.childElements.size == 2)
        println(folder_2.childElements[0].getPath())
        println((folder_2.childElements[0] as File).extension)
        folder = Folder(
            "Test_folder",
            Folder("Inner_folder_1"),
            Folder("Inner_folder_2"),
            File("Inner_file1", "txt"),
            File("Inner_file2", "log"),
            folder_2
        )
        println(folder.childElements[0].getPath())
        val inner_folder = folder.childElements[4] as Folder
        println(inner_folder.getPath())
        println(inner_folder.childElements[0].getPath())
    }
}