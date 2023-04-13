## Dominant Color Slider

This project is a simple Kotlin-based application that demonstrates a color slider with images.

The slider consists of a row of images that users can click to change the background color of the application. The
application uses an algorithm to detect the top three dominant colors of the selected image, and then animates the
background color to transition between these colors.

## Description

The getTopDominantColorsDiff function in ImageUtils.kt works by first converting the ImageBitmap to a Java BufferedImage
using the toBufferedImage function. Then, it iterates through each pixel of the image and calculates the brightness of
the color at that pixel using the brightness function.

If the brightness is greater than or equal to the brightnessThreshold value, the function then checks if the color is
similar to any of the colors already in the colorMap using the isSimilarTo function. If it is similar to an existing
color, the count of that color in the map is incremented. If not, the color is added to the map with a count of 1.

Finally, the function returns the top count colors in the colorMap, sorted by count in descending order.

The key difference between getTopDominantColorsDiff and getTopDominantColors is that getTopDominantColors counts the
occurrence of each individual color in the image, while getTopDominantColorsDiff groups similar colors together before
counting their occurrences. This can result in a different set of dominant colors being detected, depending on the image
being analyzed.

## Demo

![Demo](demo.gif)