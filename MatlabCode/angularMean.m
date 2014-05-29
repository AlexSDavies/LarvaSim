function mean = angularMean(angles)

xs = cos(angles);
ys = sin(angles);

xSum = sum(xs);
ySum = sum(ys);

mean = atan2(ySum,xSum);
