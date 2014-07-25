/* Copyright 2012 Aguzzi Cristiano

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package jada.ngeditor.guiviews;

import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.guiviews.DND.TreeTrasferHandling;
import jada.ngeditor.listeners.ElementSelectionListener;
import jada.ngeditor.listeners.PopUpShowListener;
import jada.ngeditor.listeners.events.AddElementEvent;
import jada.ngeditor.listeners.events.ReloadGuiEvent;
import jada.ngeditor.listeners.events.RemoveElementEvent;
import jada.ngeditor.listeners.events.SelectionChanged;
import jada.ngeditor.listeners.events.UpdateElementEvent;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.elements.GScreen;
import jada.ngeditor.renderUtil.NiftyTreeRender;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ActionMap;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author cris
 */
public class TreeGuiView extends javax.swing.JPanel implements Observer {

    private GUIEditor currentGui;

    /**
     * Creates new form TreeGuiView
     */
    public TreeGuiView() {
        initComponents();
        ActionMap map = this.getActionMap();
       map.put(TransferHandler.getCopyAction().getValue(javax.swing.Action.NAME),
                TransferHandler.getCopyAction());
        
        
        map.put(TransferHandler.getPasteAction().getValue(javax.swing.Action.NAME),
                TransferHandler.getPasteAction());
        map.put(TransferHandler.getCutAction().getValue(javax.swing.Action.NAME),
                TransferHandler.getCutAction());
        
       
    }

    public void initView(GUIEditor gui) {
        TreeTrasferHandling trasferHandling = new TreeTrasferHandling(gui);
        this.jTree2.setTransferHandler(trasferHandling);
        this.jTree2.addMouseListener(new PopUpShowListener(new EditingPopUp(gui)));
        this.jTree2.addTreeSelectionListener(new ElementSelectionListener(gui));
        jTree2.setCellRenderer(new NiftyTreeRender());
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.jTree2.getModel().getRoot();
        root.removeAllChildren();
        root.setUserObject(gui);

        for (GScreen screen : gui.getGui().getScreens()) {
            DefaultMutableTreeNode screenNode = new DefaultMutableTreeNode(screen);
            addRecursive(screen, screenNode);
            root.add(screenNode);
            
        }
        for (int row = 0; row < jTree2.getRowCount(); row++) {
            jTree2.expandRow(row);
        }
      
        this.jTree2.updateUI();
    }

    private void addRecursive(GElement ele, DefaultMutableTreeNode parent) {
        for (GElement sele : ele.getElements()) {
            DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(sele);
            parent.add(tmp);
            addRecursive(sele, tmp);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTree2 = new javax.swing.JTree();

        setMinimumSize(new java.awt.Dimension(80, 20));
        setPreferredSize(new java.awt.Dimension(60, 60));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("No-Gui");
        jTree2.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane2.setViewportView(jTree2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree jTree2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof AddElementEvent){
            AddElementEvent act = (AddElementEvent) arg;
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(act.getElement());
            if (!(act.getElement() instanceof GScreen)) {
                GElement e = act.getElement().getParent();
                DefaultMutableTreeNode parent = this.searchNode(e);
                parent.add(node);
                this.addRecursive(act.getElement(), node);
            } else {
                DefaultMutableTreeNode rootnode = (DefaultMutableTreeNode) jTree2.getModel().getRoot();
                rootnode.add(node);
                this.addRecursive(act.getElement(), node);
            }
            for (int row = 0; row < jTree2.getRowCount(); row++) {
                jTree2.expandRow(row);
            }
            jTree2.updateUI();
        }
         else if (arg instanceof RemoveElementEvent) {
            GElement ele = ((RemoveElementEvent)arg).getElement();
            this.searchNode(ele).removeFromParent();
            jTree2.updateUI();
        } else if (arg instanceof ReloadGuiEvent) {
            this.newGui(((GUIEditor) o));
        } else if (arg instanceof UpdateElementEvent) {
            UpdateElementEvent event = (UpdateElementEvent) arg;
            int i = currentGui.getElementEditor(event.getElement()).getIndex();
            DefaultMutableTreeNode node = searchNode(event.getElement());
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
            parent.insert(node, i);
            jTree2.updateUI();
        } else if (arg instanceof SelectionChanged){
            SelectionChanged event = (SelectionChanged) arg;
            DefaultMutableTreeNode node = this.searchNode(event.getElement());
            if(node != null){
                TreePath temp = new TreePath(node.getPath());
                jTree2.setSelectionPath(temp);
            }
            
        }
    }

    public DefaultMutableTreeNode searchNode(Object toFind) {
        DefaultMutableTreeNode node = null;
        Enumeration e = ((DefaultMutableTreeNode) jTree2.getModel().getRoot()).breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            node = (DefaultMutableTreeNode) e.nextElement();
            if (toFind.equals(node.getUserObject())) {
                return node;
            }
        }
        return null;
    }

    public void newGui(GUIEditor toChange) {
        this.currentGui = toChange;
        this.initView(toChange);
    }
}
