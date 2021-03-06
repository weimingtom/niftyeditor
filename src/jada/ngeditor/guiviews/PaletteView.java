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

import jada.ngeditor.guiviews.palettecomponents.*;
import jada.ngeditor.model.elements.GControl;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.elements.effects.GFade;
import jada.ngeditor.model.elements.effects.GMove;
import jada.ngeditor.model.elements.effects.GShake;
import jada.ngeditor.model.utils.ClassUtils;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 *
 * @author cris
 */
public class PaletteView extends javax.swing.JPanel {

    /**
     * Creates new form PaletteView
     */
    public PaletteView() {
        initComponents();
        this.addPaletteComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        elementsPane = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        controlsPane = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        effectPane = new javax.swing.JPanel();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel2.setMinimumSize(new java.awt.Dimension(66, 20));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setFont(new java.awt.Font("Simplified Arabic", 0, 14)); // NOI18N
        jLabel1.setText("Elements");
        jPanel2.add(jLabel1);

        add(jPanel2);

        elementsPane.setLayout(new java.awt.GridLayout(0, 2));
        add(elementsPane);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel2.setFont(new java.awt.Font("Simplified Arabic", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Controls");
        jPanel1.add(jLabel2);

        add(jPanel1);

        controlsPane.setLayout(new java.awt.GridLayout(0, 2));
        add(controlsPane);

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel3.setFont(new java.awt.Font("Simplified Arabic", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Effects");
        jPanel3.add(jLabel3);

        add(jPanel3);

        effectPane.setLayout(new java.awt.GridLayout(0, 2));
        add(effectPane);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlsPane;
    private javax.swing.JPanel effectPane;
    private javax.swing.JPanel elementsPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables

    private void addPaletteComponents(){
        try{
            Set<Class<? extends GElement>> classes = ClassUtils.findAllGElements();
            for(Class c : classes){
                if(this.isConcreteClass(c)){
                NWidget widget = new NWidget(c);
                if(GControl.class.isAssignableFrom(c)){
                    this.controlsPane.add(widget);
                }else
                    this.elementsPane.add(widget);
                }
            }
 
    }catch (Exception e){
        e.printStackTrace();
    }
    NWidgetEffect shake = new NWidgetEffect(new GShake());
    NWidgetEffect fade = new NWidgetEffect(new GFade());
    NWidgetEffect move = new NWidgetEffect(new GMove());
    this.effectPane.add(fade);
    this.effectPane.add(shake);
    this.effectPane.add(move);
    }
    
    private boolean isConcreteClass(Class object){
        boolean abs = Modifier.isAbstract( object.getModifiers() );
        return !abs && !object.isAnonymousClass() && GElement.class.isAssignableFrom(object);
    }
}
