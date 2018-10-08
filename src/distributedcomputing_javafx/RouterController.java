/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedcomputing_javafx;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author drew
 */
public class RouterController implements Initializable {
    
    static RouterController instance;
    
    public static void SetTable(int index, String name, String address, String port) {
        if(name != null)
            instance.friendly_fields.get(index).setText(name);
        if(address != null)
            instance.ip_fields.get(index).setText(address);
        if(port != null)
            instance.port_fields.get(index).setText(port);
    }
    
    
    
    //FXML componenets (the name needs to be same as the fxid set in .fxml)
    
    @FXML private Button but_submit;  //the button used to submit
    
    //My IP fields
    @FXML private TextField my_ip, my_port;
    
    //Routing table fields
    @FXML private TextField friendly_1, ip_1, port_1;
    @FXML private TextField friendly_2, ip_2, port_2;
    @FXML private TextField friendly_3, ip_3, port_3;
    @FXML private TextField friendly_4, ip_4, port_4;
    @FXML private TextField friendly_5, ip_5, port_5;
    @FXML private TextField friendly_6, ip_6, port_6;
    @FXML private TextField friendly_7, ip_7, port_7;
    @FXML private TextField friendly_8, ip_8, port_8;
    @FXML private TextField friendly_9, ip_9, port_9;
    @FXML private TextField friendly_10, ip_10, port_10;
    
    ArrayList<TextField> friendly_fields, ip_fields, port_fields;
    
    
    @FXML private void handleSubmitPressed(ActionEvent event) {
        Main_Router.SetMyIP(my_ip.getText(), my_port.getText());
        //disable to make it so user can not change
        my_ip.setDisable(true);
        my_port.setDisable(true);
        
        Main_Router.StartListening();
        
//        ArrayList<AddressObject> addresses = new ArrayList<AddressObject>();
//        addresses.add(new AddressObject(friendly_1.getText(), ip_1.getText(), port_1.getText()));
//        addresses.add(new AddressObject(friendly_2.getText(), ip_2.getText(), port_2.getText()));
//        addresses.add(new AddressObject(friendly_3.getText(), ip_3.getText(), port_3.getText()));
//        addresses.add(new AddressObject(friendly_4.getText(), ip_4.getText(), port_4.getText()));
//        addresses.add(new AddressObject(friendly_5.getText(), ip_5.getText(), port_5.getText()));
//        addresses.add(new AddressObject(friendly_6.getText(), ip_6.getText(), port_6.getText()));
//        addresses.add(new AddressObject(friendly_7.getText(), ip_7.getText(), port_7.getText()));
//        addresses.add(new AddressObject(friendly_8.getText(), ip_8.getText(), port_8.getText()));
//        addresses.add(new AddressObject(friendly_9.getText(), ip_9.getText(), port_9.getText()));
//        addresses.add(new AddressObject(friendly_10.getText(), ip_10.getText(), port_10.getText()));
//        Main_Router.SetRoutingTable(addresses.toArray());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        friendly_fields = new ArrayList<>();
        friendly_fields.add(friendly_1);
        friendly_fields.add(friendly_2);
        friendly_fields.add(friendly_3);
        friendly_fields.add(friendly_4);
        friendly_fields.add(friendly_5);
        friendly_fields.add(friendly_6);
        friendly_fields.add(friendly_7);
        friendly_fields.add(friendly_8);
        friendly_fields.add(friendly_9);
        friendly_fields.add(friendly_10);
        ip_fields = new ArrayList<>();
        ip_fields.add(ip_1);
        ip_fields.add(ip_2);
        ip_fields.add(ip_3);
        ip_fields.add(ip_4);
        ip_fields.add(ip_5);
        ip_fields.add(ip_6);
        ip_fields.add(ip_7);
        ip_fields.add(ip_8);
        ip_fields.add(ip_9);
        ip_fields.add(ip_10);
        port_fields = new ArrayList<>();
        port_fields.add(port_1);
        port_fields.add(port_2);
        port_fields.add(port_3);
        port_fields.add(port_4);
        port_fields.add(port_5);
        port_fields.add(port_6);
        port_fields.add(port_7);
        port_fields.add(port_8);
        port_fields.add(port_9);
        port_fields.add(port_10);
        
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String host = addr.getHostAddress(); // Client machine's IP
            my_ip.setText(host);
            my_ip.setDisable(true);
        }
        catch(Exception e) {
            System.out.println("Failed to find my own IP Address... " + e.getMessage());
        }
        
        
        // TODO
    }
}