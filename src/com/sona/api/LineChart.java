 package com.sona.api;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sampath
 */
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ToolTipManager;

public class LineChart {

    public static JPanel createChartPanel(String chartTitle, DefaultCategoryDataset dataset) {
        JFreeChart chart = createStyledLineChart(dataset, chartTitle);
        ChartPanel chartPanel = new ChartPanel(chart);

        // Set the ChartPanel with a responsive tooltip system
        chartPanel.setMouseWheelEnabled(true);  // Enable zooming with mouse wheel

        // Fine-tuning the TooltipManager for faster response
        ToolTipManager.sharedInstance().setInitialDelay(0); // No initial delay
        ToolTipManager.sharedInstance().setReshowDelay(0); // No delay when hovering again

        // Add a MouseMotionListener for instant tooltip response
        chartPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                // Force the tooltip to appear immediately when the mouse is near a data point
                chartPanel.setToolTipText(chartPanel.getToolTipText(e));
            }
        });

        chartPanel.setPreferredSize(new Dimension(900, 600));
        return chartPanel;
    }

    private static JFreeChart createStyledLineChart(DefaultCategoryDataset dataset, String chartTitle) {
        // Create a basic line chart
        JFreeChart chart = ChartFactory.createLineChart(
                chartTitle,                  // Chart title
                "📅 Week of the Month",        // X-axis label
                "📦 Units Sold",              // Y-axis label
                dataset,                      // Dataset
                PlotOrientation.VERTICAL,     // Plot Orientation (vertical)
                true,                         // Show legend
                true,                         // Show tooltips
                false                         // No URLs for links
        );

        // Get plot from the chart
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(245, 250, 255)); // Light blue background for plot area
        plot.setDomainGridlinePaint(new Color(200, 220, 240)); // Gridlines color
        plot.setRangeGridlinePaint(new Color(200, 220, 240));  // Gridlines color

        // Renderer for line chart (styling lines and adding tooltips)
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();

        // Set line colors for each series
        if(dataset.getRowKeys().size() > 0){
            renderer.setSeriesPaint(0, new Color(0, 102, 204));  // Deep Blue (for January)
        }
        if(dataset.getRowKeys().size() > 1){
            renderer.setSeriesPaint(1, new Color(51, 153, 255)); // Light Blue (for February)
        }

        // Set the line stroke width
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        if(dataset.getRowKeys().size() > 1){
            renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        }

        // Show shapes (points) at data points
        renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(true);

        // ✅ Corrected tooltip method (using setBaseToolTipGenerator)
        renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
                "{0} - {1}: {2} units",  // Tooltip format
                java.text.NumberFormat.getIntegerInstance()
        ));

        // Apply the renderer to the plot
        plot.setRenderer(renderer);

        // Set font for axis labels and tick labels
        Font axisFont = new Font("Segoe UI", Font.PLAIN, 14);
        plot.getDomainAxis().setLabelFont(axisFont);
        plot.getDomainAxis().setTickLabelFont(axisFont);
        plot.getRangeAxis().setLabelFont(axisFont);
        plot.getRangeAxis().setTickLabelFont(axisFont);

        // Customize chart background
        chart.setBackgroundPaint(Color.WHITE);

        return chart;
    }

    public static DefaultCategoryDataset createDemoDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // January Sales Data
        dataset.addValue(100, "January", "Week 1");
        dataset.addValue(120, "January", "Week 2");
        dataset.addValue(90,  "January", "Week 3");
        dataset.addValue(140, "January", "Week 4");

        // February Sales Data
        dataset.addValue(110, "February", "Week 1");
        dataset.addValue(130, "February", "Week 2");
        dataset.addValue(100, "February", "Week 3");
        dataset.addValue(150, "February", "Week 4");

        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Line Chart in JPanel Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Create your dataset
            DefaultCategoryDataset dataset = createDemoDataset();
            System.out.println(String.valueOf(dataset));

            // Create the JPanel containing the chart
            JPanel chartPanel = createChartPanel("✨ Cool Inventory Line Chart", dataset);

            // Add the chart panel to the frame
            frame.add(chartPanel, BorderLayout.CENTER);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}