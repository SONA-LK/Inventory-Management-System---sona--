/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sona.template;

/**
 *
 * @author Sampath
 */


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.text.DecimalFormat;
import javax.swing.text.html.CSS;

public class CircularProgressBarPanel {

    public static JPanel createProgressBarPanel(double targetValue, double currentValue, String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new ProgressBar(targetValue, currentValue, title), BorderLayout.CENTER);
        return panel;
    }

    private static class ProgressBar extends JPanel {
        private double progress = 0.0;
        private double targetValue = 100.0;
        private double currentValue = 0.0;
        private Color progressBarColor = new Color(65, 105, 225); // Royal Blue
        private Color baseArcColor = new Color(220, 220, 220); // Light Gray
        private Color textColor = Color.BLACK;
        private int radius = 80;
        private int strokeWidth = 15;
        private String title = "Progress";

        public ProgressBar(double targetValue, double currentValue, String title) {
            setPreferredSize(new Dimension(200, 200));
            setTargetValue(targetValue);
            setCurrentValue(currentValue);
//            setBackground(204,255,255);
            this.title = title;
//            this.setBackground(204,255,255);
            
        }

        public void setProgress(double progress) {
            this.progress = Math.max(0, Math.min(1, progress));
            repaint();
        }

        public void setCurrentValue(double currentValue) {
            this.currentValue = currentValue;
            if (targetValue > 0) {
                this.progress = currentValue / targetValue;
            } else {
                this.progress = 0; // Avoid division by zero
            }
            repaint();
        }

        public void setTargetValue(double targetValue) {
            this.targetValue = targetValue;
            if (targetValue > 0) {
                this.progress = currentValue / targetValue;
            } else {
                this.progress = 0; // Avoid division by zero
            }
            repaint();
        }

        public double getCurrentValue() {
            return currentValue;
        }

        public double getTargetValue() {
            return targetValue;
        }

        public void setProgressBarColor(Color progressBarColor) {
            this.progressBarColor = progressBarColor;
            repaint();
        }

        public void setBaseArcColor(Color baseArcColor) {
            this.baseArcColor = baseArcColor;
            repaint();
        }

        public void setTextColor(Color textColor) {
            this.textColor = textColor;
            repaint();
        }
        
        

        public void setRadius(int radius) {
            this.radius = radius;
            setPreferredSize(new Dimension(2 * radius + strokeWidth + 20, 2 * radius + strokeWidth + 50)); // Adjust size
            revalidate();
            repaint();
        }

        public void setStrokeWidth(int strokeWidth) {
            this.strokeWidth = strokeWidth;
            setPreferredSize(new Dimension(2 * radius + strokeWidth + 20, 2 * radius + strokeWidth + 50)); // Adjust size
            revalidate();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int diameter = 2 * radius;
            int startAngle = 0;
            int arcAngle = (int) Math.round(360 * progress);

            // --- Draw Title at the Top ---
            g2d.setColor(textColor);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
            Rectangle titleBounds = g2d.getFontMetrics().getStringBounds(title, g2d).getBounds();
            int titleX = centerX - titleBounds.width / 2;
            int titleY = 20; // Adjust vertical position from the top
            g2d.drawString(title, titleX, titleY);

            // --- Draw Circular Progress Bar ---
            int circleYCenter = getHeight() / 2; // Adjust center for the circle
            int circleDiameter = Math.min(getWidth() - 40, getHeight() - 80); // Adjust diameter based on available space
            int circleRadius = circleDiameter / 2;
            int circleX = centerX - circleRadius;
            int circleY = circleYCenter - circleRadius + 10; // Adjust vertical position of circle

            // Base arc
            g2d.setColor(baseArcColor);
            g2d.setStroke(new BasicStroke(strokeWidth - 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.draw(new Arc2D.Double(circleX, circleY, circleDiameter, circleDiameter, startAngle, 360, Arc2D.OPEN));
            g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); // Reset stroke

            // Progress arc
            g2d.setColor(progressBarColor);
            g2d.draw(new Arc2D.Double(circleX, circleY, circleDiameter, circleDiameter, startAngle - 90, -arcAngle, Arc2D.OPEN));

            // Draw percentage text in the center of the circle
            g2d.setFont(new Font("SansSerif", Font.BOLD, 24));
            String percentageText = new DecimalFormat("0%").format(progress);
            Rectangle stringBounds = g2d.getFontMetrics().getStringBounds(percentageText, g2d).getBounds();
            int textX = circleX + circleRadius - stringBounds.width / 2;
            int textY = circleY + circleRadius + stringBounds.height / 4;
            g2d.drawString(percentageText, textX, textY);

            // --- Draw Target and Achieved Below the Circle ---
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
            String targetLabel = "Target: ";
            String targetValueText = new DecimalFormat("#,###").format(targetValue);
            String achievedLabel = "Achieved: ";
            String achievedValueText = new DecimalFormat("#,###").format(currentValue);

            int bottomY = getHeight() - 20;
            int leftTextX = getWidth() / 4 - (g2d.getFontMetrics().stringWidth(targetLabel) + g2d.getFontMetrics().stringWidth(targetValueText) + 5) / 2;
            int rightTextX = getWidth() * 3 / 4 - (g2d.getFontMetrics().stringWidth(achievedLabel) + g2d.getFontMetrics().stringWidth(achievedValueText) + 5) / 2;

            g2d.drawString(targetLabel, leftTextX, bottomY);
            g2d.drawString(targetValueText, leftTextX + g2d.getFontMetrics().stringWidth(targetLabel) + 5, bottomY);

            g2d.drawString(achievedLabel, rightTextX, bottomY);
            g2d.drawString(achievedValueText, rightTextX + g2d.getFontMetrics().stringWidth(achievedLabel) + 5, bottomY);

            g2d.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Progress Bar in JPanel Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            JPanel progressBarPanel1 = createProgressBarPanel(100, 65, "Storage");
            frame.add(progressBarPanel1);

            JPanel progressBarPanel2 = createProgressBarPanel(150000, 90000, "Income");
            frame.add(progressBarPanel2);

            JPanel progressBarPanel3 = createProgressBarPanel(50, 30, "CPU Usage");
            frame.add(progressBarPanel3);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Example of updating progress after some time
            new Timer(2000, e -> {
                ((ProgressBar) progressBarPanel1.getComponent(0)).setCurrentValue(80);
                ((ProgressBar) progressBarPanel2.getComponent(0)).setCurrentValue(120000);
                ((ProgressBar) progressBarPanel3.getComponent(0)).setCurrentValue(45);
            }).start();
        });
    }
}