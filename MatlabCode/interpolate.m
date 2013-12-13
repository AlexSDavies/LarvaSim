function out = interpolate(vec)

s = size(vec);
cols = s(2);

i = 1;

%% Split multi-column vectors into single column
if cols > 1
	for i = 1:cols
		out(:,i) = interpolate(vec(:,i));
	end
	return;
end

n = length(vec);

while i < n
	
	% Find first nan element
	while(i < n && ~isnan(vec(i)))
		i = i+1;
	end
		
	j = i;
	
	% Find last subsequent nan element
	while(j < n && isnan(vec(j)))
		j = j+1;
	end
	
	l = j-(i-1);
	
	if (i > 1)
		startVal = vec(i-1);
	end
		
	endVal = vec(j);
	if(j == n)
		endVal = startVal;
	end
	
	if (i == 1)
		startVal = endVal;
	end
	
	inc = (endVal - startVal)/l;
	
	vec(i) = startVal + inc;
	for k = i+1:j
		vec(k) = vec(k-1) + inc;
	end
	
	i = i+1;
	
end

out = vec;