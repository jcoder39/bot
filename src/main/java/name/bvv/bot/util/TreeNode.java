package name.bvv.bot.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 04.09.2014.
 */
public final class TreeNode<T> implements Serializable
{
    private T data;
    private TreeNode<T> parent;
    public Map<T, TreeNode<T>> children;
    private static final long serialVersionUID = 15674676L;

    public TreeNode(T data)
    {
        this.data = data;
        this.children = new HashMap<>();
        this.parent = null;
    }

    public TreeNode<T> addChild(T data)
    {
        TreeNode<T> child = new TreeNode<T>(data);
        child.parent = this;
        children.put(data, child);
        return this;
    }

    public TreeNode<T> addChild(TreeNode<T> child)
    {
        child.parent = this;
        children.put(child.data, child);
        return this;
    }

    public TreeNode<T> getParent()
    {
        return parent;
    }

    public Map<T, TreeNode<T>> getChildren()
    {
        return children;
    }

    public T getData()
    {
        return data;
    }

    public TreeNode<T> getChildren(T data)
    {
        return children.get(data);
    }

    public boolean isRoot()
    {
        return parent == null;
    }

    public boolean hasChildren()
    {
        return !children.isEmpty();
    }
}
