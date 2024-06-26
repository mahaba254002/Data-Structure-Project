import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.*;

// Class representing a Customer in the bank
class Customer {
    int arrivalTime;  // The time the customer arrives at the bank
    int startTime;    // The time the customer starts being served
    int finishTime;   // The time the customer finishes being served

    public Customer(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}

// Class representing a Service Counter in the bank
class Counter {
    Queue<Customer> queue = new LinkedList<>();  // Queue of customers at the counter
    int currentTime = 0;  // Current time at the counter
    int customersServed = 0;  // Number of customers served
    int totalServiceTime = 0;  // Total service time for all customers

    // Method to serve a customer
    void serveCustomer(Customer c, int serviceTime) {
        if (currentTime < c.arrivalTime) {
            currentTime = c.arrivalTime;  // Wait until the customer arrives
        }
        c.startTime = currentTime;  // Record the start time of service
        currentTime += serviceTime;  // Update the current time by adding the service time
        c.finishTime = currentTime;  // Record the finish time of service
        totalServiceTime += serviceTime;  // Add the service time to the total service time
        customersServed++;  // Increment the number of customers served
        queue.add(c);  // Add the customer to the queue
    }
}

// Main class for the Bank GUI application
public class BankGui extends JFrame implements ActionListener {

    private JTextField numCountersField;  
    private JTextField numCustomersField; 
    private JTextField serviceTimeField;  
    private JTextField arrivalRateField;  
    private JTextPane resultsArea;  
    private JButton startSimulationButton;
    private JPanel inputPanel; 
    private JPanel resultPanel; 

    public BankGui() {
        // Set up the main window
        setTitle("Bank Simulation");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setLocationRelativeTo(null);  // Center the window on the screen
        getContentPane().setBackground(Color.DARK_GRAY); 

        // Create a welcome label
        JLabel welcomeLabel = new JLabel("Welcome to the Bank Simulation", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);

        // Create a description label
        JLabel descriptionLabel = new JLabel("<html><center>The Bank Simulator App provides the working of a real bank.<br> It shows how customers are served in the bank.</center></html>", JLabel.CENTER);
        descriptionLabel.setFont(new Font("Arial", Font.ITALIC, 15));
        descriptionLabel.setForeground(Color.LIGHT_GRAY);

        // Create an invitation label
        JLabel invitationLabel = new JLabel("Wanna learn more? Try it!!!!!", JLabel.CENTER);
        invitationLabel.setFont(new Font("Arial", Font.ITALIC, 15));
        invitationLabel.setForeground(Color.GRAY);

        // Add an image
        ImageIcon icon = new ImageIcon("pexels.jpeg");
        JLabel imageLabel = new JLabel(icon);

        // Set up the input panel with a grid layout
        inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.DARK_GRAY);

        // Create text fields for inputs
        numCountersField = new JTextField(5);
        numCustomersField = new JTextField(5);
        serviceTimeField = new JTextField(5);
        arrivalRateField = new JTextField(5);

        // Set up grid bag constraints for layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add labeled fields to the input panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(createLabeledField("Number of Service Counters:", numCountersField), gbc);
        gbc.gridx = 1;
        inputPanel.add(numCountersField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(createLabeledField("Number of Customers:", numCustomersField), gbc);
        gbc.gridx = 1;
        inputPanel.add(numCustomersField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(createLabeledField("Time taken by each customer (minutes):", serviceTimeField), gbc);
        gbc.gridx = 1;
        inputPanel.add(serviceTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(createLabeledField("Rate of Customer Arrival at the Bank (customers per 2 minutes):", arrivalRateField), gbc);
        gbc.gridx = 1;
        inputPanel.add(arrivalRateField, gbc);

        // Add the start simulation button to the input panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        startSimulationButton = new JButton("Start Simulation");
        startSimulationButton.addActionListener(this);
        inputPanel.add(startSimulationButton, gbc);

        // Set up the results area
        resultsArea = new JTextPane();
        resultsArea.setContentType("text/html");
        resultsArea.setEditable(false);
        resultsArea.setBackground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        // Set up the result panel
        resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBackground(Color.DARK_GRAY);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        // Set up the top panel with a grid layout
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 1));
        topPanel.setBackground(Color.DARK_GRAY);
        topPanel.add(welcomeLabel);
        topPanel.add(descriptionLabel);
        topPanel.add(invitationLabel);

        // Add the panels to the main frame
        add(topPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        add(resultPanel, BorderLayout.EAST);

        setVisible(true);
    }

    // Helper method to create a labeled field
    private JPanel createLabeledField(String label, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.DARK_GRAY);
        JLabel jLabel = new JLabel(label);
        jLabel.setForeground(Color.WHITE);
        panel.add(jLabel, BorderLayout.WEST);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankGui::new);
    }

    // Event handler for the start simulation button
    @Override
    public void actionPerformed(ActionEvent e) {
        // Read input values
        int numCounters = Integer.parseInt(numCountersField.getText().trim());
        int numCustomers = Integer.parseInt(numCustomersField.getText().trim());
        int serviceTime = Integer.parseInt(serviceTimeField.getText().trim());
        int arrivalRate = Integer.parseInt(arrivalRateField.getText().trim());

        // Create counters
        Counter[] counters = new Counter[numCounters];
        for (int i = 0; i < numCounters; i++) {
            counters[i] = new Counter();
        }

        // Create customers with calculated arrival times
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < numCustomers; i++) {
            int arrivalTime = (i / arrivalRate) * 2;
            customers.add(new Customer(arrivalTime));
        }

        // Simulate the bank service
        simulateBankService(counters, customers, serviceTime);
        // Display the results
        displayResults(counters, numCustomers);
    }

    // Method to simulate bank service
    private static void simulateBankService(Counter[] counters, List<Customer> customers, int serviceTime) {
        for (Customer c : customers) {
            Counter leastBusyCounter = findLeastBusyCounter(counters);
            leastBusyCounter.serveCustomer(c, serviceTime);
        }
    }

    // Method to find the least busy counter
    private static Counter findLeastBusyCounter(Counter[] counters) {
        Counter leastBusyCounter = counters[0];
        for (Counter counter : counters) {
            if (counter.currentTime < leastBusyCounter.currentTime) {
                leastBusyCounter = counter;
            }
        }
        return leastBusyCounter;
    }

    // Method to display the simulation results
    private void displayResults(Counter[] counters, int numCustomers) {
        // StringBuilder to construct the HTML formatted result
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("<html><body style='color:white; font-family:Monospaced;'>");
        resultBuilder.append("<h2>👌Simulation Results👌</h2>");
    
        int totalTime = 0;          // Variable to store the total time taken to serve all customers
        int totalWaitingTime = 0;   // Variable to store the total waiting time of all customers
    
        // Loop through each counter to gather results
        for (int i = 0; i < counters.length; i++) {
            Counter counter = counters[i];  // Get the current counter
            resultBuilder.append("<h3>Counter #").append(i + 1).append("</h3>");
            resultBuilder.append("<p>Number of customers served: ").append(counter.customersServed).append("</p>");
    
            // Update totalTime with the maximum currentTime of all counters
            totalTime = Math.max(totalTime, counter.currentTime);
    
            // Calculate total waiting time by summing up the waiting times of all customers in the queue
            totalWaitingTime += counter.queue.stream()
                    .mapToInt(c -> Math.max(0, c.startTime - c.arrivalTime))
                    .sum();
        }
    
        // Calculate total service time by summing up the service times of all counters
        int totalServiceTime = Arrays.stream(counters)
                                     .mapToInt(counter -> counter.totalServiceTime)
                                     .sum();
        // Calculate average service time per customer
        double avgServiceTime = totalServiceTime / (double) numCustomers;
    
        // Calculate average waiting time per customer
        double avgWaitingTime = totalWaitingTime / (double) numCustomers;
    
        // Append the calculated results to the resultBuilder
        resultBuilder.append("<p>Total time taken to serve all customers: ").append(totalTime).append(" minutes</p>");
        resultBuilder.append("<p>Average service time per customer: ").append(String.format("%.2f", avgServiceTime)).append(" minutes</p>");
        resultBuilder.append("<p>Average waiting time for customers: ").append(String.format("%.2f", avgWaitingTime)).append(" minutes</p>");
        resultBuilder.append("</body></html>");
    
        // Set the constructed result as the text of the resultsArea JTextPane
        resultsArea.setText(resultBuilder.toString());
    }
    
}
