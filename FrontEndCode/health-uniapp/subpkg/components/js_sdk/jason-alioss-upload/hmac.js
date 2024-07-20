import { util } from './crypto.js'

export function HMAC(hasher, message, key, options) {
  const _blocksize = 16
	// Allow arbitrary length keys
	key = key.length > _blocksize * 4 ?
	      hasher(key, { asBytes: true }) :
	      util.stringToBytes(key);

	// XOR keys with pad constants
	var okey = key,
	    ikey = key.slice(0);
	for (var i = 0; i < _blocksize * 4; i++) {
		okey[i] ^= 0x5C;
		ikey[i] ^= 0x36;
	}

	var hmacbytes = hasher(util.bytesToString(okey) +
	                       hasher(util.bytesToString(ikey) + message, { asString: true }),
	                       { asBytes: true });
	return options && options.asBytes ? hmacbytes :
	       options && options.asString ? util.bytesToString(hmacbytes) :
	       util.bytesToHex(hmacbytes);

}