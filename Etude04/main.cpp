#include <iostream>
#include <vector>

using namespace std;
int main(){
    vector<int> big_vector = {5,12,4,6,7,8,9,9,31,1,1,5,76,78,8};
    vector<int> subvector = {big_vector.begin(), big_vector.begin() + 3};
    for(int x: subvector){
        cout << endl << x;
    }
}