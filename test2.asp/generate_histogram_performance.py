import pandas as pd
import numpy as np
import plotly.graph_objs as go
from plotly.offline import plot

def generate_histogram(csv_file, output_html):
    # Read the CSV file
    data = pd.read_csv(csv_file)

    # Ensure the columns are named correctly
    if 'Execution Time (ms)' not in data.columns or 'Arguments' not in data.columns:
        raise ValueError("CSV must contain 'Execution Time (ms)' and 'Arguments' columns.")

    # Get execution times and arguments for hover info
    execution_times = data['Execution Time (ms)']
    arguments = data['Arguments'].astype(str)  # Convert to string for display
    request_numbers = np.arange(1, len(execution_times) + 1)

    # Create bar data with hover information
    bar = go.Bar(
        x=request_numbers,
        y=execution_times,
        name='Execution Times',
        marker=dict(color='blue', opacity=0.75),
        hoverinfo='text',
        text=[f'Execution Time: {time} ms<br>Arguments: {arg}' for time, arg in zip(execution_times, arguments)]
    )

    # Calculate percentiles
    p70 = np.percentile(execution_times, 70)
    p80 = np.percentile(execution_times, 80)
    p90 = np.percentile(execution_times, 90)
    p99 = np.percentile(execution_times, 99)  # Calculate P99

    # Create layout for the plot
    layout = go.Layout(
        title='Execution Times per Request',
        xaxis=dict(title='Request Number'),
        yaxis=dict(title='Execution Time (ms)'),
        showlegend=True
    )

    # Create a figure
    fig = go.Figure(data=[bar], layout=layout)
    
    # Add percentile lines
    percentiles = [p70, p80, p90, p99]
    colors = ['orange', 'yellowgreen', 'red', 'purple']  # Colors for P70, P80, P90, P99
    labels = [f'P70: {p70:.2f} ms', f'P80: {p80:.2f} ms', f'P90: {p90:.2f} ms', f'P99: {p99:.2f} ms']

    for p, color, label in zip(percentiles, colors, labels):
        fig.add_trace(go.Scatter(
            x=[0, len(request_numbers)],  # Extend the x-axis across the full width
            y=[p, p],  # Same y-value for the percentile
            mode='lines',
            name=label,
            line=dict(color=color, width=2, dash='dash')
        ))

    # Save the figure as an HTML file
    plot(fig, filename=output_html, auto_open=False)

    print(f'Histogram generated and saved as {output_html}.')

# Usage
csv_file = 'execution_times.csv'  # Input CSV file path
output_html = 'execution_time_histogram.html'  # Output HTML file path
generate_histogram(csv_file, output_html)
