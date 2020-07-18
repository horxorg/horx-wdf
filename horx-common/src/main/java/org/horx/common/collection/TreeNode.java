package org.horx.common.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 树结构的节点。
 * @param <T> 树节点对象类型。
 * @param <K> 树节点对象的ID类型。
 * @since 1.0
 */
public class TreeNode<T, K> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 未选择。
     */
    public static final int UNCHECKED = 0;
    /**
     * 节点被选中。
     */
    public static final int CHECKED = 1;
    /**
     * 节点及子节点被选中。
     */
    public static final int CHECKED_ALL = 2;

    /**
     * 树节点中的数据。
     */
    private T data;

    /**
     * 父节点。
     */
    private TreeNode<T, K> parent;

    /**
     * 节点id。
     */
    private K id;

    /**
     * 父节点id。
     */
    private K parentId;

    /**
     * 节点选择标志。
     */
    private int checkStatus;

    /**
     * 叠加节点选择标志
     */
    private int overlyingCheckStatus;

    /**
     * 得到节点所在级别。
     * @return
     */
    public int getLevel() {
        int level = 1;
        TreeNode<T, K> node = this.getParent();
        while (node != null) {
            level += 1;
            node = node.getParent();
        }
        return level;
    }

    /**
     * 子节点。
     */
    private List<TreeNode<T, K>> children = new ArrayList<>();

    /**
     * 添加子节点。
     * @param child
     */
    public void addChild(TreeNode<T, K> child) {
        children.add(child);
        child.setParent(this);
    }

    /**
     * 在特定位置处添加子节点。
     * @param index
     * @param child
     */
    public void addChild(int index, TreeNode<T, K> child) {
        children.add(index, child);
        child.setParent(this);
    }

    /**
     * 设置子节点。
     * @param children
     */
    public void setChildren(List<TreeNode<T, K>> children) {
        for (int i=0; i<children.size(); i++) {
            children.get(i).setParent(null);
        }

        this.children.clear();

        if (children != null) {
            for (int i=0; i<children.size(); i++) {
                addChild(children.get(i));
            }
        }
    }

    /**
     * 获取所有子节点。
     * @return
     */
    public List<TreeNode<T, K>> getChildren() {
        return this.children;
    }

    /**
     * 移除子节点。
     * @return
     */
    public boolean removeChildren() {
        boolean result = false;
        if (this.children != null && this.children.size() > 0) {
            for (int i = this.children.size() - 1; i >= 0; i--) {
                this.children.get(i).desdroy();
            }
            this.children.clear();
            result = true;
        }
        return result;
    }

    /**
     * 移除子节点。
     * @param i
     * @return
     */
    public boolean removeChild(int i) {
        boolean result = false;
        if (this.children != null && this.children.size() > 0 && this.children.size() > i) {
            this.children.get(i).desdroy();
            this.children.remove(i);
            result = true;
        }
        return result;
    }

    /**
     * 获取所有下级节点。
     * @return
     */
    public List<TreeNode<T, K>> getAllSubNodes() {
        List<TreeNode<T, K>> result = new ArrayList<>();
        getSubNodes(result);
        return result;
    }

    /**
     * 获取本身和所有下级节点。
     * @return
     */
    public List<TreeNode<T, K>> getSelfAndSubNodes() {
        List<TreeNode<T, K>> result = new ArrayList<>();
        result.add(this);
        getSubNodes(result);
        return result;
    }

    /**
     * 选择。
     */
    public void check() {
        if (checkStatus == CHECKED || checkStatus == CHECKED_ALL) {
            return;
        }
        checkStatus = CHECKED;

    }

    /**
     * 叠加选择。
     */
    public void overlyingCheck() {
        if (checkStatus == UNCHECKED) {
            return;
        }
        if (overlyingCheckStatus == CHECKED || overlyingCheckStatus == CHECKED_ALL) {
            return;
        }
        overlyingCheckStatus = CHECKED;
    }

    /**
     * 选择节点和子节点。
     */
    public void checkAll() {
        if (checkStatus == CHECKED_ALL) {
            return;
        }
        checkStatus = CHECKED_ALL;
        if (children != null) {
            for (TreeNode<T, K> child : children) {
                child.checkAll();
            }
        }
    }

    /**
     * 叠加选择节点和子节点。
     */
    public void overlyingCheckAll() {
        if (checkStatus == UNCHECKED && !hasCheckedSubNode()) {
            return;
        }
        if (checkStatus != UNCHECKED) {
            overlyingCheckStatus = checkStatus;
        }
        if (children != null) {
            for (TreeNode<T, K> child : children) {
                child.overlyingCheckAll();
            }
        }
    }

    /**
     * 是否有选中的子节点。
     * @return
     */
    public boolean hasCheckedSubNode() {
        if (children == null || children.isEmpty()) {
            return false;
        }

        for (TreeNode<T, K> node : children) {
            if (node.getCheckStatus() != UNCHECKED) {
                return true;
            }

            if (node.hasCheckedSubNode()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否有叠加选中的子节点。
     * @return
     */
    public boolean hasOverlyingCheckedSubNode() {
        if (children == null || children.isEmpty()) {
            return false;
        }

        for (TreeNode<T, K> node : children) {
            if (node.getOverlyingCheckStatus() != UNCHECKED) {
                return true;
            }

            if (node.hasOverlyingCheckedSubNode()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 复制本节点及下级节点。
     * @param isDeep
     * @return
     */
    public TreeNode<T, K> clone(boolean isDeep) {
        return cloneNode(isDeep);
    }

    /**
     * 销毁节点。
     */
    public void desdroy() {
        if (this.children != null && this.children.size() > 0) {
            for (int i = 0; i < this.children.size(); i++) {
                if (this.children.get(i) == null) continue;
                this.children.get(i).desdroy();
            }

            this.children.clear();
        }
        this.setData(null);
        this.children.clear();
        this.setId(null);
        this.setParent(null);
        this.setParentId(null);
    }

    /**
     * 得到所有父节点，根结点在首位。
     * @return
     */
    public List<TreeNode<T, K>> getParents() {
        List<TreeNode<T, K>> result = new ArrayList<>();
        TreeNode<T, K> node = this.getParent();
        while (node != null) {
            result.add(node);
            node = node.getParent();
        }
        Collections.reverse(result);
        return result;
    }

    /**
     * 得到所有父节点和本身，根结点在首位。
     * @return
     */
    public List<TreeNode<T, K>> getParentsAndSelf() {
        List<TreeNode<T, K>> result = getParents();
        result.add(this);
        return result;
    }

    /**
     * 设置节点数据。
     * @param data 节点数据。
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 获取节点数据。
     * @return 节点数据。
     */
    public T getData() {
        return this.data;
    }

    /**
     * 设置父节点。
     * @param p 父节点。
     */
    public void setParent(TreeNode<T, K> p) {
        this.parent = p;
    }

    /**
     * 获取父节点。
     * @return 父节点。
     */
    public TreeNode<T, K> getParent() {
        return this.parent;
    }

    /**
     * 设置数据ID。
     * @param id 数据ID。
     */
    public void setId(K id) {
        this.id = id;
    }

    /**
     * 获取数据ID。
     * @return 数据ID。
     */
    public K getId() {
        return this.id;
    }

    /**
     * 设置父节点ID。
     * @param parentId 父节点ID。
     */
    public void setParentId(K parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取父节点ID。
     * @return 父节点ID。
     */
    public K getParentId() {
        return this.parentId;
    }

    /**
     * 获取check状态。
     * @return check状态。
     */
    public int getCheckStatus() {
        return checkStatus;
    }

    /**
     * 获取叠加check状态。
     * @return 叠加check状态。
     */
    public int getOverlyingCheckStatus() {
        return overlyingCheckStatus;
    }

    /**
     * 递归复制本节点及下级节点。
     * @param isDeep
     * @return
     */
    private TreeNode<T, K> cloneNode(boolean isDeep) {
        TreeNode<T, K> newNode = new TreeNode<>();
        newNode.setData(data);
        newNode.setParent(null);
        newNode.setId(id);
        newNode.setParentId(parentId);
        newNode.checkStatus = getCheckStatus();
        if (isDeep) {
            for (int i = 0; i < this.children.size(); i++) {
                if (this.children.get(i) == null) continue;
                TreeNode<T, K> child = this.children.get(i).cloneNode(isDeep);
                newNode.addChild(child);
            }
        }
        return newNode;
    }

    /**
     * 递归累加下级节点。
     * @param nodes
     */
    private void getSubNodes(List<TreeNode<T, K>> nodes) {
        if (this.children == null) return;

        for (int i = 0; i < this.children.size(); i++) {
            if (this.children.get(i) == null) continue;
            nodes.add(this.children.get(i));
        }

        for (int i = 0; i < this.children.size(); i++) {
            if (this.children.get(i) == null) continue;
            this.children.get(i).getSubNodes(nodes);;
        }

    }
}
