package org.horx.common.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据结构-树。
 * @param <T> 树节点对象类型。
 * @param <K> 树节点对象的ID类型。
 * @since 1.0
 */
public class Tree<T, K> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 所有节点。
     */
    private Map<K, TreeNode<T, K>> allNodes = new LinkedHashMap<>();
    /**
     * 所有根节点。
     */
    private List<TreeNode<T, K>> roots = new ArrayList<>();
    /**
     * 根节点标志。
     */
    private K rootParentId;

    /**
     * 获取所有根节点。
     * @return
     */
    public List<TreeNode<T, K>> getRoots() {
        return this.roots;
    }

    /**
     * 获取节点。
     * @param id
     * @return
     */
    public TreeNode<T, K> getNode(K id) {
        if (allNodes.containsKey(id)) return allNodes.get(id);
        return null;
    }

    /**
     * 获取所有节点
     * @return
     */
    public Collection<TreeNode<T, K>> getAllNodes() {
        return allNodes.values();
    }

    /**
     * 添加节点。
     * @param data
     * @param id
     * @param parentId
     */
    public void addNode(T data, K id, K parentId) {
        TreeNode<T, K> node = new TreeNode<>();
        node.setData(data);
        node.setId(id);
        node.setParentId(parentId);
        allNodes.put(id, node);

    }

    /**
     * 添加根节点。
     * @param root
     */
    public void addRootNode(TreeNode<T, K> root) {
        if (root == null) return;
        this.roots.add(root);

        List<TreeNode<T, K>> nodes = root.getSelfAndSubNodes();
        for (int i = 0, len = nodes.size(); i < len; i++)
        {
            allNodes.put(nodes.get(i).getId(), nodes.get(i));
        }
    }

    /**
     * 根据根节点特征值构建树。可能会存在不在树上的节点。
     * @param rootParentId
     */
    public void buildTree(K rootParentId) {
        buildTree(rootParentId, false);
    }

    /**
     * 根据根节点特征值构建树。可能会存在不在树上的节点。
     * @param rootParentId
     * @param upgradeToRoot 如果根据特征值没有根节点，则寻找没有父节点的节点作为根界面。
     */
    public void buildTree(K rootParentId, boolean upgradeToRoot) {
        this.rootParentId = rootParentId;

        for (K key : allNodes.keySet()) {

            TreeNode<T, K> node = allNodes.get(key);

            if (rootParentId.equals(node.getParentId()))
            {
                roots.add(node);
            }
            else
            {
                if (allNodes.containsKey(node.getParentId()))
                {
                    TreeNode<T, K> parent = allNodes.get(node.getParentId());
                    parent.addChild(node);
                }
            }
        }

        if (allNodes.size() > 0 && upgradeToRoot) {
            upgradeNoRarentNode();
        }
    }

    /**
     * 构建树。如果没有父节点则置为根节点。
     */
    public void buildTree() {
        for (K key : allNodes.keySet()) {
            TreeNode<T, K> node = allNodes.get(key);
            if (allNodes.containsKey(node.getParentId()))
            {
                TreeNode<T, K> parent = allNodes.get(node.getParentId());
                parent.addChild(node);
            }
        }

        upgradeNoRarentNode();
    }

    /**
     * 把没有父节点的节点提升为根节点。
     */
    public void upgradeNoRarentNode() {
        for (TreeNode<T, K> node : allNodes.values()) {
            if (node.getParent() == null && !roots.contains(node)) {
                roots.add(node);
            }
        }
    }

    /**
     * 复制。
     */
    public Tree<T, K> clone() {
        Tree<T, K> newTree = new Tree<>();
        for (K key : allNodes.keySet()) {
            TreeNode<T, K> node = allNodes.get(key);
            TreeNode<T, K> newTreeNode = node.clone(false);
            newTree.allNodes.put(newTreeNode.getId(), newTreeNode);
        }

        newTree.buildTree(rootParentId);
        return newTree;
    }

    /**
     * 销毁。
     */
    public void destroy() {
        for (int i=0,len=roots.size(); i<len; i++)
        {
            roots.get(i).desdroy();
        }
        roots.clear();
        roots = null;
        allNodes.clear();
        allNodes = null;
        rootParentId = null;
    }

    /**
     * 转为可序列化的树形数据。
     * @return
     */
    public List<T> convertToTreeData() {
       return convertToTreeData(false);
    }

    /**
     * 转为可序列化的树形数据。
     * @param onlyChecked 是否不转换有checked标志的节点。
     * @return
     */
    public List<T> convertToTreeData(boolean onlyChecked) {
        List<T> list = convertTreeNode(getRoots(), onlyChecked);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    private List<T> convertTreeNode(List<TreeNode<T, K>> nodeList, boolean onlyChecked) {
        if (nodeList == null || nodeList.size() == 0) {
            return null;
        }
        List<T> list = new ArrayList<>(nodeList.size());
        for (TreeNode<T, K> node : nodeList) {
            if (onlyChecked && node.getCheckStatus() == TreeNode.UNCHECKED && !node.hasCheckedSubNode()) {
                continue;
            }
            T data = node.getData();
            List<T> children = convertTreeNode(node.getChildren(), onlyChecked);
            if (children != null && data instanceof TreeData) {
                TreeData treeData = (TreeData) data;
                treeData.setChildren(children);
            }
            list.add(data);
        }


        return list;
    }
}
