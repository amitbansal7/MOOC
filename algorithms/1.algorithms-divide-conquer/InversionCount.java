import java.util.*;
public class InversionCount{

	static private long count(int[] A, int n){
		if(n<=1)
			return 0;

		int mid = n/2;
		int Al[] = new int[mid];
		int Ar[] = new int[n-mid];

		for(int i=0;i<mid;i++)
			Al[i] = A[i];

		for(int i=mid;i<n;i++)
			Ar[i-mid] = A[i];

		long x = count(Al, mid);
		long y = count(Ar, n-mid);
		long z = 0;

		int l = 0;
		int k = 0;
		int i = 0;

		while(l< mid && k < n - mid)
		{
			if(Al[l] <= Ar[k])
				A[i++] = Al[l++];
			else
			{
				A[i++] = Ar[k++];
				z += (mid - l);
			}
		}

		while(l<mid)
			A[i++] = Al[l++];

		while(k < n - mid)
			A[i++] = Ar[k++];

		return x + y + z;
	}

	public static void main(String[] args) {
		//Answer : 2407905288
		int n = 100000;
		int A[] = new int[n];

		Scanner input = new Scanner(System.in);

		for(int i=0;i<n;i++)
			A[i] = input.nextInt();

		System.out.println(count(A,n));
	}
}
