import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

public class Main extends JFrame {

    private JTextArea basicInfo;
    private JPanel mainPanel,secondPanel,thirdPanel,fourthPanel,fifthPanel;
    private JButton addButton,editButton,saveButton,deleteButton,searchButton,moreButton,
                    loadButton,displayAllButton,displayAllByISBNButton,displayAllByTitleButton,exitButton;
    private JTextField ISBNInput, titleInput;
    private JTable table1;
    private JScrollPane pane1;
    private JLabel ISBN,title;
    private int counter = 0;

    private MyLinkedList<Book> bookLinkedList = new MyLinkedList<Book>();

    private ArrayList<String> sortList = new ArrayList<>();
    private ArrayList<String> tempSortList = new ArrayList<>();

    public boolean checkISBN(String ISBN){
        Iterator<Book> it = bookLinkedList.iterator();
        Book bk1 = null;
        while (it.hasNext()){
            bk1 = it.next();
            if (bk1.getISBN().equals(ISBN)){
                return false;
            }
        }
        return true;
    }

    public void buttonChangeState(boolean state){
        addButton.setEnabled(state);
        editButton.setEnabled(state);
        deleteButton.setEnabled(state);
        searchButton.setEnabled(state);
        moreButton.setEnabled(state);
        loadButton.setEnabled(state);
        displayAllButton.setEnabled(state);
        displayAllByTitleButton.setEnabled(state);
        displayAllByISBNButton.setEnabled(state);
        saveButton.setEnabled(!state);

    }



    public Main(){
        setTitle("Library Admin System");
        setSize(1000,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(1000,500));
        mainPanel.setLayout(new GridLayout(4,1));

        basicInfo = new JTextArea();
        basicInfo.setText("Name and Id: Fok Lik Hang 20050661d");
        basicInfo.setPreferredSize(new Dimension(500,50));
        Date date = java.util.Calendar.getInstance().getTime();
        String strDate = date.toString();
        basicInfo.append("\n"+strDate);
        basicInfo.setEditable(false);

        String[] rowTitle = {"ISBN","Title","Available"};
        Object[][] tableData = new Object[0][3];
        table1 = new JTable();
        table1.setModel(new DefaultTableModel(tableData,rowTitle));
        table1.setRowSelectionAllowed(true);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pane1 = new JScrollPane(table1);

        secondPanel = new JPanel();
        secondPanel.setLayout(new GridLayout(0,1));

        thirdPanel =new JPanel();
        thirdPanel.setSize(600,100);
        thirdPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        ISBNInput = new JTextField("",20);
        titleInput = new JTextField("",20);
        ISBN =new JLabel("ISBN ");
        title = new JLabel("Title ");
        thirdPanel.add(ISBN);
        thirdPanel.add(ISBNInput);
        thirdPanel.add(title);
        thirdPanel.add(titleInput);

        fourthPanel = new JPanel();
        fourthPanel.setSize(700,100);
        fourthPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        fourthPanel.setSize(700,100);
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        saveButton = new JButton("Save");
        saveButton.setEnabled(false);
        deleteButton = new JButton("Delete");
        searchButton = new JButton("Search");
        moreButton = new JButton("More>>");
        fourthPanel.add(addButton);
        fourthPanel.add(editButton);
        fourthPanel.add(saveButton);
        fourthPanel.add(deleteButton);
        fourthPanel.add(searchButton);
        fourthPanel.add(moreButton);

        fifthPanel = new JPanel();
        fifthPanel.setSize(800,100);
        fifthPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        loadButton = new JButton("Load Test Data");
        displayAllButton = new JButton("Display All");
        displayAllByISBNButton = new JButton("Display All By ISBN");
        displayAllByTitleButton = new JButton("Display All By Title");
        exitButton = new JButton("Exit");
        fifthPanel.add(loadButton);
        fifthPanel.add(displayAllButton);
        fifthPanel.add(displayAllByISBNButton);
        fifthPanel.add(displayAllByTitleButton);
        fifthPanel.add(exitButton);

        secondPanel.add(thirdPanel);
        secondPanel.add(fourthPanel);
        secondPanel.add(fifthPanel);

        mainPanel.add(basicInfo);
        mainPanel.add(pane1);
        mainPanel.add(secondPanel);
        add(mainPanel);
        setVisible(true);

        DefaultTableModel tableModel=(DefaultTableModel) table1.getModel();

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyLinkedList<Book> temp = new MyLinkedList<Book> ();
                Book book1 = new Book();
                book1.setISBN("0131450913");
                book1.setTitle("HTML How to Program");
                book1.setAvailable(true);
                temp.add(book1);

                Book book2 = new Book();
                book2.setISBN("0131857576");
                book2.setTitle("C++ How to Program");
                book2.setAvailable(true);
                temp.add(book2);

                Book book3 = new Book();
                book3.setISBN("0132222205");
                book3.setTitle("Java How to Program");
                book3.setAvailable(false);
                temp.add(book3);

                Iterator<Book> it = temp.iterator();
                Book bk = null;
               while (it.hasNext()) {
                    bk = it.next();
                    if(checkISBN(bk.getISBN())){
                        bookLinkedList.add(bk);
                        tableModel.addRow(tableData);
                        tableModel.setValueAt(bk.getISBN(),counter,0);
                        tableModel.setValueAt(bk.getTitle(),counter,1);
                        tableModel.setValueAt(bk.isAvailable(),counter,2);
                        counter++;
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Error: book ISBN exists in current database");
                    }
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ISBNInput.getText().isEmpty()||titleInput.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Error: ISBN and Title must be entered");
                }
                else{
                    Book book = new Book();
                    book.setISBN(ISBNInput.getText());
                    book.setTitle(titleInput.getText());
                    book.setAvailable(true);

                    if(checkISBN(book.getISBN())){
                        bookLinkedList.add(book);
                        tableModel.addRow(tableData);
                        tableModel.setValueAt(book.getISBN(),counter,0);
                        tableModel.setValueAt(book.getTitle(),counter,1);
                        tableModel.setValueAt(book.isAvailable(),counter,2);
                        counter++;
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Error: book ISBN exists in current database");
                    }
                }
            }

        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRowIndex = table1.getSelectedRow();
                ISBNInput.setText(tableModel.getValueAt(selectedRowIndex,0).toString());
                titleInput.setText(tableModel.getValueAt(selectedRowIndex,1).toString());
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkISBN(ISBNInput.getText())==false){
                    buttonChangeState(false);
                    Iterator<Book> it = bookLinkedList.iterator();
                    Book bk = null;
                    while (it.hasNext()){
                        bk = it.next();
                        if (bk.getISBN().equals(ISBNInput.getText())){
                            break;
                        }
                    }
                    titleInput.setText(bk.getTitle());
                    ISBNInput.setEditable(false);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Error: book ISBN doesn't exists in current database");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Iterator<Book> it = bookLinkedList.iterator();
                Book bk = null;
                while (it.hasNext()){
                    bk = it.next();
                    if (bk.getISBN().equals(ISBNInput.getText())){
                        break;
                    }
                }
                String tempTitle = bk.getTitle();
                bk.setTitle(titleInput.getText());

                for (int i = 0;i<tableModel.getRowCount();i++){

                    if (tableModel.getValueAt(i,1).equals(tempTitle)){
                        tableModel.setValueAt(bk.getTitle(),i,1);
                        break;
                    }

                }
                buttonChangeState(true);
                table1.repaint();
                ISBNInput.setEditable(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkISBN(ISBNInput.getText())==false){
                    Iterator<Book> it = bookLinkedList.iterator();
                    Book bk = null;
                    while (it.hasNext()){
                        bk = it.next();
                        if (bk.getISBN().equals(ISBNInput.getText())){
                            break;
                        }
                    }
                    for (int i = 0;i<tableModel.getRowCount();i++){
                        if (tableModel.getValueAt(i,0).equals(bk.getISBN())){
                            tableModel.removeRow(i);
                            bookLinkedList.remove(i);
                            counter--;
                            break;
                        }
                        table1.repaint();
                    }
                    ISBNInput.setText("");
                    titleInput.setText("");
                }
                else{
                    JOptionPane.showMessageDialog(null,"Error: book ISBN exists in current database");
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Iterator<Book> it = bookLinkedList.iterator();
                Book bk = null;
                int tempCounter = 0;
                tableModel.setRowCount(0);
                while (it.hasNext()){
                    bk = it.next();
                    tableModel.addRow(tableData);
                    tableModel.setValueAt(bk.getISBN(),tempCounter,0);
                    tableModel.setValueAt(bk.getTitle(),tempCounter,1);
                    tableModel.setValueAt(bk.isAvailable(),tempCounter,2);
                    tempCounter++;
                }

                tempCounter = 0;
                it = bookLinkedList.iterator();
                bk = null;

                while (it.hasNext()){
                    bk = it.next();
                    if ((bk.getISBN().contains(ISBNInput.getText())||bk.getTitle().equals(titleInput.getText()))==false){
                        tableModel.removeRow(tempCounter);
                        tempCounter--;
                    }
                    tempCounter++;
                }

            }
        });

        displayAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Iterator<Book> it = bookLinkedList.iterator();
                Book bk = null;
                int tempCounter10 = 0;
                tableModel.setRowCount(0);
                while (it.hasNext()){
                    bk = it.next();
                    tableModel.addRow(tableData);
                    tableModel.setValueAt(bk.getISBN(),tempCounter10,0);
                    tableModel.setValueAt(bk.getTitle(),tempCounter10,1);
                    tableModel.setValueAt(bk.isAvailable(),tempCounter10,2);
                    tempCounter10++;
                }
            }
        });

        displayAllByISBNButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                sortList.clear();
                tempSortList.clear();
                Iterator<Book> it = bookLinkedList.iterator();
                Book bk = null;
                int tempCounter = 0;
                while (it.hasNext()){
                    bk = it.next();
                    sortList.add(bk.getISBN());
                }
                tempSortList = sortList;

                Collections.sort(sortList);


                if (tempSortList.equals(sortList)){
                    if(counter == 1){
                        Collections.sort(sortList);
                        counter = 0;
                    }
                    else{
                        Collections.sort(sortList,Collections.reverseOrder());
                        counter = 1;
                    }
                }



                for (int i= 0;i<sortList.size();i++){
                    it = bookLinkedList.iterator();
                    bk = null;
                    tableModel.addRow(tableData);
                    tableModel.setValueAt(sortList.get(i),i,0);
                    while(it.hasNext()){
                        bk = it.next();
                        if (bk.getISBN().equals(sortList.get(i))){
                            tableModel.setValueAt(bk.getTitle(),i,1);
                            tableModel.setValueAt(bk.isAvailable(),i,2);
                        }
                    }
                }
            }
        });

        displayAllByTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                sortList.clear();
                tempSortList.clear();
                Iterator<Book> it = bookLinkedList.iterator();
                Book bk = null;
                int tempCounter = 0;
                while (it.hasNext()){
                    bk = it.next();
                    sortList.add(bk.getTitle());
                }
                tempSortList = sortList;
                Collections.sort(sortList);

                if (tempSortList.equals(sortList)){
                    if(counter == 1){
                        Collections.sort(sortList);
                        counter = 0;
                    }
                    else{
                        Collections.sort(sortList,Collections.reverseOrder());
                        counter = 1;
                    }
                }

                for (int i= 0;i<sortList.size();i++){
                    it = bookLinkedList.iterator();
                    bk = null;
                    tableModel.addRow(tableData);
                    tableModel.setValueAt(sortList.get(i),i,1);
                    while(it.hasNext()){
                        bk = it.next();
                        if (bk.getTitle().equals(sortList.get(i))){
                            tableModel.setValueAt(bk.getISBN(),i,0);
                            tableModel.setValueAt(bk.isAvailable(),i,2);
                        }
                    }
                }
            }
        });

        moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ISBNInput.getText().isEmpty()||bookLinkedList.size()==0){
                    JOptionPane.showMessageDialog(null,"Error");
                }
                else {
                    JFrame f = new JFrame();
                    f.setSize(600, 350);
                    f.setTitle(titleInput.getText());

                    JPanel extraJPanel = new JPanel();
                    extraJPanel.setPreferredSize(new Dimension(450, 350));
                    extraJPanel.setLayout(new GridLayout(4, 1));

                    JTextArea basicThings = new JTextArea();
                    basicThings.setText("ISBN:" + ISBNInput.getText());
                    basicThings.append("\nTitle: " + titleInput.getText());


                    Iterator<Book> it = bookLinkedList.iterator();
                    Book bk = null;
                    while (it.hasNext()) {
                        bk = it.next();
                        if (bk.getTitle().equals(titleInput.getText())) {
                            break;
                        }
                    }
                    basicThings.append("\nAvailable: " + bk.isAvailable());

                    JPanel extraSecondPanel = new JPanel();
                    extraSecondPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

                    JButton borrowButton = new JButton("Borrow");
                    JButton returnButton = new JButton("Return");
                    JButton reserveButton = new JButton("Reserve");
                    JButton waitQueueButton = new JButton("Waiting Queue");
                    JButton findOnline = new JButton("Find Online");
                    extraSecondPanel.add(borrowButton);
                    extraSecondPanel.add(returnButton);
                    extraSecondPanel.add(reserveButton);
                    extraSecondPanel.add(waitQueueButton);
                    extraSecondPanel.add(findOnline);

                    JTextArea outputTextArea = new JTextArea("");

                    if(bk.isAvailable()){
                        returnButton.setEnabled(false);
                        reserveButton.setEnabled(false);
                        waitQueueButton.setEnabled(false);
                    }
                    else{
                        borrowButton.setEnabled(false);
                    }

                    extraJPanel.add(basicThings);
                    extraJPanel.add(extraSecondPanel);
                    extraJPanel.add(outputTextArea);

                    f.add(extraJPanel);
                    f.setVisible(true);

                    borrowButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Iterator<Book> it = bookLinkedList.iterator();
                            Book bk = null;
                            while (it.hasNext()) {
                                bk = it.next();
                                if (bk.getTitle().equals(titleInput.getText())) {
                                    break;
                                }
                            }
                            if (bk.isAvailable() == false) {
                                outputTextArea.setText("The book is borrowed");
                            } else {
                                bk.setAvailable(false);
                            }
                            if(bk.isAvailable()){
                                returnButton.setEnabled(false);
                                reserveButton.setEnabled(false);
                                waitQueueButton.setEnabled(false);
                                borrowButton.setEnabled(true);
                            }
                            else{
                                borrowButton.setEnabled(false);
                                returnButton.setEnabled(true);
                                reserveButton.setEnabled(true);
                                waitQueueButton.setEnabled(true);
                            }
                            basicThings.selectAll();
                            basicThings.replaceSelection("");
                            basicThings.setText("ISBN:" + ISBNInput.getText());
                            basicThings.append("\nTitle: " + titleInput.getText());
                            basicThings.append("\nAvailable: " + bk.isAvailable());
                            basicThings.repaint();
                        }
                    });

                    reserveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Iterator<Book> it = bookLinkedList.iterator();
                            Book bk = null;

                            while (it.hasNext()) {
                                bk = it.next();
                                if (bk.getTitle().equals(titleInput.getText())) {
                                    break;
                                }
                            }
                            if(bk.isAvailable()==false){
                                String tempMessage = JOptionPane.showInputDialog(null, "What is your name ?");
                                MyQueue<String> queue = bk.getReservedQueue();
                                queue.enqueue(tempMessage);
                                outputTextArea.setText("The book is reserved by " + tempMessage);
                            }
                            else {
                                JOptionPane.showMessageDialog(null,"You can borrow the book");
                            }

                        }

                    });

                    waitQueueButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Iterator<Book> it = bookLinkedList.iterator();
                            Book bk = null;
                            while (it.hasNext()) {
                                bk = it.next();
                                if (bk.getTitle().equals(titleInput.getText())) {
                                    break;
                                }
                            }
                            MyQueue<String> queue = bk.getReservedQueue();
                            outputTextArea.setText("The waiting queue");
                            for (String name : queue.getList()) {
                                outputTextArea.setText(outputTextArea.getText() + "\n" + name);
                            }
                            outputTextArea.repaint();
                        }
                    });

                    returnButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Iterator<Book> it = bookLinkedList.iterator();
                            Book bk = null;
                            while (it.hasNext()) {
                                bk = it.next();
                                if (bk.getTitle().equals(titleInput.getText())) {
                                    break;
                                }
                            }

                            MyQueue<String> queue = bk.getReservedQueue();
                            if (queue.getSize() == 0) {
                                bk.setAvailable(true);
                                basicThings.selectAll();
                                basicThings.replaceSelection("");
                                basicThings.setText("ISBN:" + ISBNInput.getText());
                                basicThings.append("\nTitle: " + titleInput.getText());
                                basicThings.append("\nAvailable: " + bk.isAvailable());
                                outputTextArea.setText("The book is return\n");
                                basicThings.repaint();
                            } else {
                                String tempName = queue.dequeue();
                                outputTextArea.setText("The book is return\n");
                                outputTextArea.append("The book is borrowed by " + tempName);

                            }
                            if(bk.isAvailable()){
                                returnButton.setEnabled(false);
                                reserveButton.setEnabled(false);
                                waitQueueButton.setEnabled(false);
                                borrowButton.setEnabled(true);
                            }
                            else{
                                borrowButton.setEnabled(false);
                                returnButton.setEnabled(true);
                                reserveButton.setEnabled(true);
                                waitQueueButton.setEnabled(true);
                            }
                        }
                    });

                    findOnline.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                String tempSearch = titleInput.getText();
                                String webPage = String.format("http://www.google.com/search?q=%s", tempSearch.replaceAll(" ", "+"));
                                Desktop.getDesktop().browse(new URI(webPage));
                            } catch (Exception error) {
                            }
                        }
                    });
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });

    }

    public static void main(String args[]){
        Main myFrame = new Main();
    }

}