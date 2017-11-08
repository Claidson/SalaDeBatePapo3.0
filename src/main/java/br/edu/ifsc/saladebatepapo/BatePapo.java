/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsc.saladebatepapo;

import static br.edu.ifsc.saladebatepapo.CriptografiaRSA.PATH_CHAVE_PRIVADA;
import br.edu.ifsc.saladebatepapo.serverRSA.ClientChaves;
import br.edu.ifsc.saladebatepapo.serverRSA.ServidorDeChave;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author aluno
 */
public class BatePapo extends javax.swing.JFrame {

    /**
     * Creates new form BatePapo
     */
    ConectarThread conectar;
    DefaultListModel listModel;
    CriptografiaAES criptografia;
    Boolean conectou;
    Boolean recebeuChave;
    String IpServidorChave;
    ClientChaves clientRSA;
    byte[] chaveAES;
    ServidorDeChave servidorChave = new ServidorDeChave();
    String chaveDescripgrafada;

    //parte do cisual tela
    private UIManager.LookAndFeelInfo[] looks
            = UIManager.getInstalledLookAndFeels();
    private JRadioButton[] escolha = new JRadioButton[looks.length];
    private ButtonGroup grupoLooks = new ButtonGroup();

    public BatePapo() {
        initComponents();
        criptografia = new CriptografiaAES();
        clientRSA = new ClientChaves();
        conectou = false;
        recebeuChave = false;
        getRootPane().setDefaultButton(jButtonEnviar);
        adicionaLookMenu();
        desabilitaBotoesServidorChaves();

    }

    public void adicionaLookMenu() {
        ItemSelecionado iselect = new ItemSelecionado();
        for (int i = 0; i < looks.length; i++) {
            escolha[i] = new JRadioButton(looks[i].getName());
            escolha[i].addItemListener(iselect);
            grupoLooks.add(escolha[i]);
            jMenuVisualizar.add(escolha[i]);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        jPanelNome = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldNome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldGrupo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldPorta = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxSkin = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jRadioButLocal = new javax.swing.JRadioButton();
        jToggleButBuscarChaveLocal = new javax.swing.JToggleButton();
        jRadioButRemoto = new javax.swing.JRadioButton();
        jToggleButBuscarChaveRemota = new javax.swing.JToggleButton();
        jTextFieldIP = new javax.swing.JTextField();
        jPanelServidor = new javax.swing.JPanel();
        jToggleChat = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListBatePapo = new javax.swing.JList<>();
        jButtonEnviar = new javax.swing.JButton();
        jTextFieldMensagem = new javax.swing.JTextField();
        jMenuVisualizar = new javax.swing.JToolBar();

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nome");

        jLabel3.setText("Grupo");

        jTextFieldGrupo.setText("228.5.6.7");

        jLabel2.setText("Porta");

        jTextFieldPorta.setText("6666");
        jTextFieldPorta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPortaActionPerformed(evt);
            }
        });

        jLabel5.setText("Skin");

        jComboBoxSkin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Padrão", "Dark", "Matrix" }));
        jComboBoxSkin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSkinActionPerformed(evt);
            }
        });

        jLabel4.setText("Servidor chaves");

        jRadioButLocal.setText("Local");
        jRadioButLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButLocalActionPerformed(evt);
            }
        });

        jToggleButBuscarChaveLocal.setText("Iniciar ");
        jToggleButBuscarChaveLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButBuscarChaveLocalActionPerformed(evt);
            }
        });

        jRadioButRemoto.setText("Remoto");
        jRadioButRemoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButRemotoActionPerformed(evt);
            }
        });

        jToggleButBuscarChaveRemota.setText("Buscar");
        jToggleButBuscarChaveRemota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButBuscarChaveRemotaActionPerformed(evt);
            }
        });

        jTextFieldIP.setText("10.151.34.51");
        jTextFieldIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelNomeLayout = new javax.swing.GroupLayout(jPanelNome);
        jPanelNome.setLayout(jPanelNomeLayout);
        jPanelNomeLayout.setHorizontalGroup(
            jPanelNomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNomeLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanelNomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNomeLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxSkin, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelNomeLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPorta, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelNomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelNomeLayout.createSequentialGroup()
                                .addComponent(jRadioButRemoto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldIP, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jToggleButBuscarChaveRemota))
                            .addGroup(jPanelNomeLayout.createSequentialGroup()
                                .addComponent(jRadioButLocal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jToggleButBuscarChaveLocal)))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanelNomeLayout.setVerticalGroup(
            jPanelNomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxSkin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldPorta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jRadioButLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButBuscarChaveLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addGroup(jPanelNomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButRemoto)
                    .addComponent(jToggleButBuscarChaveRemota, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jToggleChat.setText("Entrar");
        jToggleChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleChatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelServidorLayout = new javax.swing.GroupLayout(jPanelServidor);
        jPanelServidor.setLayout(jPanelServidorLayout);
        jPanelServidorLayout.setHorizontalGroup(
            jPanelServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelServidorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleChat, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelServidorLayout.setVerticalGroup(
            jPanelServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToggleChat)
        );

        jListBatePapo.setFocusable(false);
        jScrollPane1.setViewportView(jListBatePapo);

        jButtonEnviar.setText("Enviar");
        jButtonEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnviarActionPerformed(evt);
            }
        });

        jTextFieldMensagem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldMensagemKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextFieldMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanelNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanelNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEnviar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenuVisualizar.setRollover(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addComponent(jMenuVisualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jMenuVisualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jTextFieldIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIPActionPerformed

    private void jComboBoxSkinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSkinActionPerformed
        // TODO add your handling code here:
        if (jComboBoxSkin.getSelectedIndex() == 1) {
            Color corFundo = Color.BLACK;
            Color corFonte = Color.WHITE;
            jPanel1.setBackground(corFundo);
            jPanelNome.setBackground(corFundo);
            jLabel1.setForeground(corFonte);
            jLabel2.setForeground(corFonte);
            jLabel3.setForeground(corFonte);
            jLabel4.setForeground(corFonte);
            jLabel5.setForeground(corFonte);
            jRadioButLocal.setForeground(corFonte);
            jRadioButRemoto.setForeground(corFonte);
            jListBatePapo.setBackground(Color.DARK_GRAY);
            jListBatePapo.setForeground(Color.WHITE);

        } else if (jComboBoxSkin.getSelectedIndex() == 2) {
            Color corFundo = Color.darkGray;
            Color corFonte = Color.GREEN;
            jPanel1.setBackground(corFundo);
            jPanelNome.setBackground(corFundo);
            jLabel1.setForeground(corFonte);
            jLabel2.setForeground(corFonte);
            jLabel3.setForeground(corFonte);
            jLabel4.setForeground(corFonte);
            jLabel5.setForeground(corFonte);
            jRadioButLocal.setForeground(corFonte);
            jRadioButRemoto.setForeground(corFonte);
            jListBatePapo.setBackground(corFundo);
            jListBatePapo.setForeground(corFonte);
        } else if (jComboBoxSkin.getSelectedIndex() == 0) {
            Color corFundo = null;
            Color corFonte = Color.BLACK;
            jPanel1.setBackground(corFundo);
            jPanelNome.setBackground(corFundo);
            jLabel1.setForeground(corFonte);
            jLabel2.setForeground(corFonte);
            jLabel3.setForeground(corFonte);
            jLabel4.setForeground(corFonte);
            jLabel5.setForeground(corFonte);
            jRadioButLocal.setForeground(corFonte);
            jRadioButRemoto.setForeground(corFonte);
            jListBatePapo.setBackground(Color.WHITE);
            jListBatePapo.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jComboBoxSkinActionPerformed
    private void receberChave(String ip) throws IOException, ClassNotFoundException {
        if (ip.equals("localhost")) {
            try {

                servidorChave.start();
                System.out.println("chave local");
            } catch (IllegalThreadStateException e) {
                System.out.println("Pau na thread local de receber chaves " + e);
            }

        }
        try {

            System.out.println("vai ir a chave");
            clientRSA.enviaChavePublica(ip);
            System.out.println("Foi a chave");

            Thread.sleep(2000);

            System.out.println("aki antes de receber");
            clientRSA.receberArquivoCriptografado(ip);

            System.out.println("depois de receber");
            chaveDescripgrafada = descriptografaRSA();
            System.out.println("Chave descriptografada: " + chaveDescripgrafada);

        } catch (ClassNotFoundException | InterruptedException | ConnectException ex) {
            Logger.getLogger(BatePapo.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("Pau no metodo de receber chaves" + ex);
        }

    }

    private void entrarChat() {
        if (jTextFieldGrupo.getText().equals("")
                || jTextFieldNome.getText().equals("") || jTextFieldPorta.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
        } else if (Integer.parseInt(jTextFieldPorta.getText()) < 1 || Integer.parseInt(jTextFieldPorta.getText()) > 65535) {
            JOptionPane.showMessageDialog(null, "Porta deve ser entre 1 - 65535");
        } else if (chaveDescripgrafada == null) {
            JOptionPane.showMessageDialog(null, "Verifique se o servidor de chaves está rodando");
        } else {

            conectar = new ConectarThread(Integer.parseInt(jTextFieldPorta.getText()),
                    jTextFieldNome.getText(), this, jTextFieldGrupo.getText(), chaveDescripgrafada);
            System.out.println("Conectado");
            listModel = new DefaultListModel();
            jListBatePapo.setModel(listModel);
            conectar.start();

            conectou = true;
            System.out.println("conectou: " + conectou);
            String nome = jTextFieldNome.getText();
            String texto = "Entrou na sala";
            prepararMensagem(nome, texto);
            habilitaCampos();
        }
    }
    private void jTextFieldPortaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPortaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPortaActionPerformed

    private void jRadioButLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButLocalActionPerformed
        jRadioButRemoto.setSelected(false);
        desabilitaBotoesServidorChaves();
        jToggleButBuscarChaveLocal.setVisible(true);

    }//GEN-LAST:event_jRadioButLocalActionPerformed

    private void jToggleButBuscarChaveLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButBuscarChaveLocalActionPerformed

        if (jToggleButBuscarChaveLocal.isSelected()) {
            IpServidorChave = "localhost";
            try {
                receberChave(IpServidorChave);
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("pau ao receber chave local");
                Logger.getLogger(BatePapo.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (chaveDescripgrafada != null) {
                jToggleButBuscarChaveLocal.setText("Recebeu");
                jToggleButBuscarChaveLocal.setBackground(Color.YELLOW);

            } else {
                jToggleButBuscarChaveLocal.setText("Buscar");
                jToggleButBuscarChaveLocal.setSelected(false);
                JOptionPane.showMessageDialog(null, "Verifique servidor de chaves local");
            }

        } else {

            jToggleButBuscarChaveLocal.setText("Buscar");
        }

    }//GEN-LAST:event_jToggleButBuscarChaveLocalActionPerformed

    private void jToggleButBuscarChaveRemotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButBuscarChaveRemotaActionPerformed
        if (jToggleButBuscarChaveRemota.isSelected()) {
            IpServidorChave = jTextFieldIP.getText();
            try {
                receberChave(IpServidorChave);
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("pau ao receber chave remota");
                Logger.getLogger(BatePapo.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (chaveDescripgrafada != null) {
                jToggleButBuscarChaveRemota.setText("Recebeu");
                jToggleButBuscarChaveRemota.setBackground(Color.YELLOW);

            } else {
                jToggleButBuscarChaveRemota.setText("Buscar");
                jToggleButBuscarChaveRemota.setSelected(false);
                JOptionPane.showMessageDialog(null, "Verifique servidor de chaves");
            }

        } else {

            jToggleButBuscarChaveRemota.setText("Buscar");
        }


    }//GEN-LAST:event_jToggleButBuscarChaveRemotaActionPerformed

    private void jRadioButRemotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButRemotoActionPerformed
        jRadioButLocal.setSelected(false);
        desabilitaBotoesServidorChaves();
        jToggleButBuscarChaveRemota.setVisible(true);
        jTextFieldIP.setVisible(true);
    }//GEN-LAST:event_jRadioButRemotoActionPerformed

    private void jToggleChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleChatActionPerformed
        if (jToggleChat.isSelected()) {

            entrarChat();
            if (conectou) {
                jToggleChat.setText("Sair");
            } else {
                jToggleChat.setText("Entrar");
                jToggleChat.setSelected(false);
            }

        } else {
            //  JOptionPane.showMessageDialog(null, "Entre na sala primeiro!");
            String nome = jTextFieldNome.getText();
            String texto = "Saiu da sala";
            prepararMensagem(nome, texto);
            conectar.parar();
            conectou = false;
            habilitaCampos();
            jToggleChat.setText("Entrar");
        }


    }//GEN-LAST:event_jToggleChatActionPerformed

    private void jButtonEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnviarActionPerformed

        if (jTextFieldGrupo.getText().equals("")
                || jTextFieldNome.getText().equals("") || jTextFieldPorta.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
        } else if (conectou) {
            System.out.println("conectou enviar: " + conectou);
            String nome = jTextFieldNome.getText();
            String texto = jTextFieldMensagem.getText();
            jTextFieldMensagem.setText("");
            jTextFieldMensagem.requestFocus();
            prepararMensagem(nome, texto);
        } else {
            JOptionPane.showMessageDialog(null, "Entre na sala primeiro!");
        }
    }//GEN-LAST:event_jButtonEnviarActionPerformed

    private void jTextFieldMensagemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMensagemKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            //jButtonEnviar.doClick();
            //aqui vai o q voce deseja fazer quando o usuario clicar enter naquele jtextfield
        }
    }//GEN-LAST:event_jTextFieldMensagemKeyPressed

    public void inserirTexto(String texto) {
        listModel.addElement(texto);

    }

    public void desabilitaBotoesServidorChaves() {
        jTextFieldIP.setVisible(false);
        jToggleButBuscarChaveLocal.setVisible(false);
        jToggleButBuscarChaveRemota.setVisible(false);
    }

    public void criptografaRSA(String msgOriginal, String ArquivoChavePublica) throws FileNotFoundException, IOException, ClassNotFoundException {

        ObjectInputStream inputStream = null;

        // Criptografa a Mensagem usando a Chave Pública
        inputStream = new ObjectInputStream(new FileInputStream(ArquivoChavePublica));
        PublicKey chavePublica = (PublicKey) inputStream.readObject();
        chaveAES = CriptografiaRSA.criptografa(msgOriginal, chavePublica);
    }

    public String descriptografaRSA() throws FileNotFoundException, IOException, ClassNotFoundException {

        ObjectInputStream inputStreamChavePrivada = null;
        System.out.println("antes de ler os arquivos");
        inputStreamChavePrivada = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PRIVADA));
        System.out.println("Leu os arquivos");
        PrivateKey chavePrivada = (PrivateKey) inputStreamChavePrivada.readObject();
        File sourceFile = new File("chaveConexaoCriptografada.key");
        byte[] criptografado = getBytes(sourceFile);
        String textoPuro = CriptografiaRSA.decriptografa(criptografado, chavePrivada);
        System.out.println("metodo de criptografar: " + textoPuro);
        return textoPuro;

    }

    public byte[] getBytes(File file) {
        int len = (int) file.length();
        byte[] sendBuf = new byte[len];
        FileInputStream inFile = null;
        try {
            inFile = new FileInputStream(file);
            inFile.read(sendBuf, 0, len);
        } catch (FileNotFoundException fnfex) {
        } catch (IOException ioex) {
        }
        return sendBuf;
    }

    public void prepararMensagem(String nome, String texto) {

        String mensagemOriginal;
        mensagemOriginal = nome + " Diz: " + texto;
        System.out.println("mensagem original" + mensagemOriginal + " Chave: " + chaveDescripgrafada);
        byte[] criptografado = null;
        try {
            criptografado = criptografia.criptografar(mensagemOriginal, chaveDescripgrafada);
            System.out.println("criptografado: " + Arrays.toString(criptografado));
            String descriptografado = criptografia.descriptografar(criptografado, chaveDescripgrafada);
            System.out.println("Descriptografado no try:" + descriptografado);

        } catch (Exception ex) {
            Logger.getLogger(BatePapo.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println(
                    "pau no criptografar");
        }

        enviarMensagem(criptografado);
    }

    public void enviarMensagem(byte[] data) {
        conectar.enviarMensagem(data);

    }

    public void habilitaCampos() {

        jTextFieldGrupo.setEnabled(!jTextFieldGrupo.isEnabled());
        jTextFieldNome.setEnabled(!jTextFieldNome.isEnabled());
        jTextFieldPorta.setEnabled(!jTextFieldPorta.isEnabled());
        jTextFieldIP.setEnabled(!jTextFieldIP.isEnabled());

    }

    private class ItemSelecionado implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            for (int i = 0; i < escolha.length; i++) {
                if (escolha[i].isSelected()) {
                    atualiza(i);
                }
            }
        }
    }

    public void atualiza(int i) {
        try {
            UIManager.setLookAndFeel(looks[i].getClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BatePapo.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BatePapo.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BatePapo.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BatePapo.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BatePapo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonEnviar;
    private javax.swing.JComboBox<String> jComboBoxSkin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList<String> jListBatePapo;
    private javax.swing.JToolBar jMenuVisualizar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelNome;
    private javax.swing.JPanel jPanelServidor;
    private javax.swing.JRadioButton jRadioButLocal;
    private javax.swing.JRadioButton jRadioButRemoto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldGrupo;
    private javax.swing.JTextField jTextFieldIP;
    private javax.swing.JTextField jTextFieldMensagem;
    private javax.swing.JTextField jTextFieldNome;
    private javax.swing.JTextField jTextFieldPorta;
    private javax.swing.JToggleButton jToggleButBuscarChaveLocal;
    private javax.swing.JToggleButton jToggleButBuscarChaveRemota;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleChat;
    // End of variables declaration//GEN-END:variables
}
